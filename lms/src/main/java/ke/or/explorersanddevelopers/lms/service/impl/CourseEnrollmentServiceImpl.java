package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.exception.DuplicateRecordException;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.CourseEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import ke.or.explorersanddevelopers.lms.repositories.CourseEnrollmentRepository;
import ke.or.explorersanddevelopers.lms.repositories.CourseRepository;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import ke.or.explorersanddevelopers.lms.service.CourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseEnrollmentMapper courseEnrollmentMapper;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private static void setSubTopicAsCompleted(Map<BigDecimal, Set<BigDecimal>> completedTopicsForResponse, BigDecimal topicId, BigDecimal subTopicId) {
        log.info("Adding subtopic " + subTopicId + " as completed in topic " + topicId);
        if (completedTopicsForResponse.containsKey(topicId)) {
            // if key (topicId id) exists push the subtopic to its value
            completedTopicsForResponse.get(topicId).add(subTopicId);
        } else {
            // if key (topicId id) doesn't exist add the key and a set of value
            Set<BigDecimal> value = new HashSet<>();
            value.add(subTopicId);
            completedTopicsForResponse.put(topicId, value);
        }
    }

    public static String convertListItemsIntoCommaSeperatedString(List<String> listItems) {
        StringBuilder finalCompletedSubTopics = new StringBuilder();
        int count = 1;
        for (String id : listItems) {
            if (count != listItems.size())
                finalCompletedSubTopics.append(id).append(",");
            else
                finalCompletedSubTopics.append(id);
            count += 1;
        }
        return finalCompletedSubTopics.toString();
    }

    private Student getStudentByIdFromDb(BigDecimal studentId) {
        return studentRepository.getByStudentId(studentId).orElseThrow(() -> new NoSuchRecordException("Student not found"));
    }

    @Override
    public CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId) {
        Student student = getStudentByIdFromDb(studentId);
        Course course = getCourseById(courseId);
        boolean alreadyEnrolled = courseEnrollmentRepository.existsByCourseAndStudent(course, student);

        if (alreadyEnrolled)
            throw new DuplicateRecordException("Student already enrolled for the course");

        CourseEnrollment courseEnrollment = CourseEnrollment.builder()
                .testEnrollments(new HashSet<>())
                .amount(course.getPrice())
                .status(StatusEnum.PENDING)
                .student(student)
                .course(course)
                .build();

        CourseEnrollment savedCourseEnrollment = courseEnrollmentRepository.save(courseEnrollment);

        return courseEnrollmentMapper.toDto(savedCourseEnrollment);
    }

    @Override
    public List<CourseEnrollmentDto> getCourseEnrollments(Pageable pageable) {
        List<CourseEnrollmentDto> response = new ArrayList<>();
        courseEnrollmentRepository.findAll(pageable).forEach(courseEnrollment -> response.add(courseEnrollmentMapper.toDto(courseEnrollment)));
        return response;
    }

    @Override
    public List<CourseEnrollmentDto> getCourseEnrollmentByStudentId(BigDecimal studentId) {
        log.info("Getting course enrollment by student id");
        Student student = getStudentByIdFromDb(studentId);
        List<CourseEnrollmentDto> response = new ArrayList<>();
        courseEnrollmentRepository.findByStudent(student)
                .forEach(courseEnrollment -> response.add(courseEnrollmentMapper.toDto(courseEnrollment)));
        return response;
    }

    @Override
    public CourseEnrollmentDto completeSubTopic(BigDecimal courseEnrollmentId, BigDecimal subTopicId) {
        log.info("Updating a course enrollment [" + courseEnrollmentId + "].Completing the sub-topic [" + subTopicId + "]");
        CourseEnrollment oldCourseEnrollment = courseEnrollmentRepository.findByCourseEnrollmentId(courseEnrollmentId)
                .orElseThrow(() -> {
                    log.error("Error find course enrollment with id: " + courseEnrollmentId);
                    return new NoSuchRecordException("No such course enrollment: " + courseEnrollmentId);
                });
        log.info("Currently completed topics are : " + oldCourseEnrollment.getCompletedTopicsIds());
        log.info("Currently completed sub-topics are : " + oldCourseEnrollment.getCompletedSubTopicsIds());
        // get completed topics
        String completedSubTopicsIds = oldCourseEnrollment.getCompletedSubTopicsIds();

        List<String> completedSubTopicsIdsList = new ArrayList<>();

        if (completedSubTopicsIds != null && !completedSubTopicsIds.isEmpty() && !completedSubTopicsIds.isBlank())
            completedSubTopicsIdsList.addAll(List.of(completedSubTopicsIds.split(",")));

        // check if subtopic already completed
        boolean alreadyCompleted = completedSubTopicsIdsList.contains(subTopicId.toPlainString());
        if (alreadyCompleted)
            throw new DuplicateRecordException("Student already completed this subtopic");

        // check if subtopic exists
        Map<BigDecimal, Set<BigDecimal>> completedSubTopicsForResponse = new HashMap<>();
        List<String> completedTopicsForResponse = new ArrayList<>();

        Set<Topic> allTopics = oldCourseEnrollment.getCourse().getTopics();

        // complete subtopics
        allTopics.forEach(topic -> {
            List<BigDecimal> subTopicIds = topic.getSubTopics().stream()
                    .flatMap(item -> Stream.of(item.getSubTopicId()))
                    .collect(Collectors.toList());
            subTopicIds.forEach(currentSubtopicId -> {
                log.debug("Does " + completedSubTopicsIdsList + " contain " + currentSubtopicId.toString() + " ? " + completedSubTopicsIdsList.contains("" + currentSubtopicId.intValueExact()));
                if (completedSubTopicsIdsList.contains("" + currentSubtopicId.intValueExact())) {
                    setSubTopicAsCompleted(completedSubTopicsForResponse, topic.getTopicId(), currentSubtopicId);
                }
                if (currentSubtopicId.intValueExact() == subTopicId.intValueExact()) {
                    setSubTopicAsCompleted(completedSubTopicsForResponse, topic.getTopicId(), currentSubtopicId);
                    completedSubTopicsIdsList.add(subTopicId.toPlainString());
                }
            });

        });

        // update completed topics
        completedSubTopicsForResponse.forEach((topicId, completedSubtopics) -> {
            List<Topic> topicList = allTopics.stream()
                    .filter(item -> item.getTopicId().intValueExact() == topicId.intValueExact()) // get the current topic
                    .collect(Collectors.toList());

            if (!topicList.isEmpty()) {
                topicList.forEach(topic -> {
                    if (topic.getSubTopics().size() == completedSubtopics.size()) {
                        log.info("Completing topic of id " + topicId);
                        completedTopicsForResponse.add(topic.getTopicId().toPlainString());
                    }
                });
            }
        });

        // save updates to course enrollment
        oldCourseEnrollment.setCompletedTopicsIds(convertListItemsIntoCommaSeperatedString(completedTopicsForResponse));
        oldCourseEnrollment.setCompletedSubTopicsIds(convertListItemsIntoCommaSeperatedString(completedSubTopicsIdsList));
        CourseEnrollment updatedCourseEnrollment = courseEnrollmentRepository.save(oldCourseEnrollment);

        // construct the response
        CourseEnrollmentDto updatedCourseEnrollmentDto = courseEnrollmentMapper.toDto(updatedCourseEnrollment);
        updatedCourseEnrollmentDto.setCompletedSubTopicsIds(completedSubTopicsForResponse);
        updatedCourseEnrollmentDto.setCompletedTopicsIds(completedTopicsForResponse.stream().map(Double::parseDouble).map(BigDecimal::valueOf).collect(Collectors.toList()));

        return updatedCourseEnrollmentDto;
    }

    private Course getCourseById(BigDecimal courseId) {
        return courseRepository.getByCourseId(courseId).orElseThrow(() -> new NoSuchRecordException("Course not found"));
    }
}
