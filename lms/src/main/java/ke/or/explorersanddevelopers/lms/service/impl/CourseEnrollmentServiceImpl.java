package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.exception.DuplicateRecordException;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.CourseEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.model.entity.SubTopic;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseEnrollmentMapper courseEnrollmentMapper;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private static void setSubTopicAsCompleted(Map<BigDecimal, Set<BigDecimal>> completedTopicsForResponse, BigDecimal topicId, BigDecimal subTopicId) {
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

    private Student getStudentByIdFromDb(BigDecimal studentId) {
        return studentRepository.getByStudentId(studentId).orElseThrow(() -> new NoSuchRecordException("Student not found"));
    }

    @Override
    public CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId) {
        Student student = getStudentByIdFromDb(studentId);
        Course course = getCourseById(courseId);
        Boolean alreadyEnrolled = courseEnrollmentRepository.existsByCourseAndStudent(course, student);

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
                .forEach(courseEnrollment -> {
                    response.add(courseEnrollmentMapper.toDto(courseEnrollment));
                });
        return response;
    }

    @Override
    public CourseEnrollmentDto completeSubTopic(BigDecimal courseEnrollmentId, BigDecimal subTopicId) {
        log.info("Updating a course enrollment [" + courseEnrollmentId + "] adding a completed topic [" + subTopicId + "]");
        CourseEnrollment oldCourseEnrollment = courseEnrollmentRepository.findByCourseEnrollmentId(courseEnrollmentId);
        String completedTopicsIds = oldCourseEnrollment.getCompletedTopicsIds();
        List<String> completedTopicsIdsList = new ArrayList<>();

        if (completedTopicsIds != null && !completedTopicsIds.isEmpty() && !completedTopicsIds.isBlank())
            completedTopicsIdsList.addAll(List.of(completedTopicsIds.split(",")));

        // check if subtopic already completed
        boolean alreadyCompleted = completedTopicsIdsList.contains(subTopicId.toPlainString());
        if (alreadyCompleted)
            throw new DuplicateRecordException("Student already completed the subtopic with id: " + subTopicId);

        // check if subtopic exists
        Map<BigDecimal, Set<BigDecimal>> completedTopicsForResponse = new HashMap<>();
        oldCourseEnrollment.getCourse().getTopics().forEach(topic -> {
            List<SubTopic> subTopics = topic.getSubTopics().stream()
                    .filter(subTopic -> {
                        Integer currentSubtopicId = subTopic.getSubTopicId().intValueExact();
                        if (completedTopicsIdsList.contains(currentSubtopicId.toString())) {
                            setSubTopicAsCompleted(completedTopicsForResponse, topic.getTopicId(), subTopic.getSubTopicId());
                        }
                        boolean subtopicFound = currentSubtopicId.equals(subTopicId.intValueExact());
                        if (subtopicFound) {
                            setSubTopicAsCompleted(completedTopicsForResponse, topic.getTopicId(), subTopic.getSubTopicId());
                        }
                        return subtopicFound;
                    }).collect(Collectors.toList());

            if (!subTopics.isEmpty()) {
                completedTopicsIdsList.add(subTopicId.toPlainString());
            }
        });

        // save updates
        StringBuilder finalCompletedTopics = new StringBuilder();
        int count = 1;
        for (String id : completedTopicsIdsList) {
            if (count != completedTopicsIdsList.size())
                finalCompletedTopics.append(id).append(",");
            else
                finalCompletedTopics.append(id);
            count += 1;
        }

        oldCourseEnrollment.setCompletedTopicsIds(finalCompletedTopics.toString());
        CourseEnrollment updatedCourseEnrollment = courseEnrollmentRepository.save(oldCourseEnrollment);

        // retrieve the topics and check for subtopic
        CourseEnrollmentDto updatedCourseEnrollmentDto = courseEnrollmentMapper.toDto(updatedCourseEnrollment);
        updatedCourseEnrollmentDto.setCompletedTopics(completedTopicsForResponse);

        return updatedCourseEnrollmentDto;
    }

    private Course getCourseById(BigDecimal courseId) {
        return courseRepository.getByCourseId(courseId).orElseThrow(() -> new NoSuchRecordException("Course not found"));
    }
}
