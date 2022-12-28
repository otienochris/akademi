package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.CourseEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.mappers.CourseMapper;
import ke.or.explorersanddevelopers.lms.mappers.StudentMapper;
import ke.or.explorersanddevelopers.lms.mappers.TestEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.model.entity.TestEnrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ke.or.explorersanddevelopers.lms.service.impl.CourseEnrollmentServiceImpl.convertListItemsIntoCommaSeperatedString;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
public class CourseEnrollmentMapperDecorator implements CourseEnrollmentMapper {

    @Autowired
    @Qualifier("delegate")
    private CourseEnrollmentMapper courseEnrollmentMapper;

    @Autowired
    @Qualifier("delegate")
    private StudentMapper studentMapper;

    @Autowired
    @Qualifier("delegate")
    private CourseMapper courseMapper;

    @Autowired
    @Qualifier("delegate")
    private TestEnrollmentMapper testEnrollmentMapper;


    @Override
    public CourseEnrollmentDto toDto(CourseEnrollment courseEnrollment) {
        CourseEnrollmentDto mappedCourseEnrollmentDto = courseEnrollmentMapper.toDto(courseEnrollment);
        // set student
        Student student = courseEnrollment.getStudent();
        if (student != null) {
            StudentDto studentDto = studentMapper.toDto(student);
            mappedCourseEnrollmentDto.setStudent(studentDto);
        }

        // set course
        Course course = courseEnrollment.getCourse();
        if (course != null) {
            CourseDto courseDto = courseMapper.toDto(course);
            mappedCourseEnrollmentDto.setCourse(courseDto);
        }

        // set test enrollments
        mappedCourseEnrollmentDto.setTestEnrollments(new HashSet<>());
        Set<TestEnrollment> testEnrollments = courseEnrollment.getTestEnrollments();
        if (testEnrollments != null && !testEnrollments.isEmpty()) {
            testEnrollments.forEach(testEnrollment -> mappedCourseEnrollmentDto.getTestEnrollments().add(testEnrollmentMapper.toDto(testEnrollment)));
        }

        // set the completed sub topics
        String completedSubTopicsIds = courseEnrollment.getCompletedSubTopicsIds();
        List<String> completedSubTopicsIdsList = new ArrayList<>();

        if (completedSubTopicsIds != null && !completedSubTopicsIds.isEmpty() && !completedSubTopicsIds.isBlank())
            completedSubTopicsIdsList.addAll(List.of(completedSubTopicsIds.split(",")));

        Map<BigDecimal, Set<BigDecimal>> completedSubTopicsForResponse = new HashMap<>();
        courseEnrollment.getCourse().getTopics()
                .forEach(topic -> topic.getSubTopics()
                        .forEach(subTopic -> {
                            if (completedSubTopicsIdsList.contains(Integer.toString(subTopic.getSubTopicId().intValueExact())))
                                setSubTopicAsCompleted(completedSubTopicsForResponse, topic.getTopicId(), subTopic.getSubTopicId());
                        }));
        mappedCourseEnrollmentDto.setCompletedSubTopicsIds(completedSubTopicsForResponse);

        // set the completed topics
        String completedTopicsIds = courseEnrollment.getCompletedTopicsIds();
        if (completedTopicsIds != null && !completedTopicsIds.isEmpty() && !completedTopicsIds.isBlank()) {
            List<BigDecimal> ids = Arrays.stream(completedTopicsIds.split(",")).mapToDouble(Double::parseDouble).mapToObj(BigDecimal::valueOf).collect(Collectors.toList());
            mappedCourseEnrollmentDto.setCompletedTopicsIds(ids);
        }

        return mappedCourseEnrollmentDto;
    }

    private void setSubTopicAsCompleted(Map<BigDecimal, Set<BigDecimal>> completedSubTopicsForResponse, BigDecimal topicId, BigDecimal subTopicId) {
        if (completedSubTopicsForResponse.containsKey(topicId)) {
            // if key (topicId id) exists push the subtopic to its value
            completedSubTopicsForResponse.get(topicId).add(subTopicId);
        } else {
            // if key (topicId id) doesn't exist add the key and a set of value
            Set<BigDecimal> value = new HashSet<>();
            value.add(subTopicId);
            completedSubTopicsForResponse.put(topicId, value);
        }
    }

    @Override
    public CourseEnrollment toEntity(CourseEnrollmentDto courseEnrollmentDto) {
        CourseEnrollment mappedCourseEnrollment = courseEnrollmentMapper.toEntity(courseEnrollmentDto);

        // set studentDto
        StudentDto studentDto = courseEnrollmentDto.getStudent();
        if (studentDto != null) {
            Student student = studentMapper.toEntity(studentDto);
            mappedCourseEnrollment.setStudent(student);
        }

        // set course
        CourseDto courseDto = courseEnrollmentDto.getCourse();
        if (courseDto != null) {
            Course course = courseMapper.toEntity(courseDto);
            mappedCourseEnrollment.setCourse(course);
        }

        // set test enrollments
        mappedCourseEnrollment.setTestEnrollments(new HashSet<>());
        Set<TestEnrollmentDto> testEnrollmentDtoList = courseEnrollmentDto.getTestEnrollments();
        if (testEnrollmentDtoList != null && !testEnrollmentDtoList.isEmpty()) {
            testEnrollmentDtoList.forEach(testEnrollment -> mappedCourseEnrollment.getTestEnrollments().add(testEnrollmentMapper.toEntity(testEnrollment)));
        }

        // set completed subtopics
        Map<BigDecimal, Set<BigDecimal>> completedSubTopicsIds = courseEnrollmentDto.getCompletedSubTopicsIds();
        List<String> completedSubTopics = new ArrayList<>();
        completedSubTopicsIds.forEach((topicId, subtopicIds) -> subtopicIds.forEach(id -> completedSubTopics.add(id.toPlainString())));
        mappedCourseEnrollment.setCompletedSubTopicsIds(convertListItemsIntoCommaSeperatedString(completedSubTopics));

        // set the completed topics
        List<BigDecimal> completedTopicsIds = courseEnrollmentDto.getCompletedTopicsIds();
        mappedCourseEnrollment.setCompletedTopicsIds(convertListItemsIntoCommaSeperatedString(completedTopicsIds.stream().flatMap(item -> Stream.of(String.valueOf(item.intValueExact()))).collect(Collectors.toList())));

        return mappedCourseEnrollment;
    }
}
