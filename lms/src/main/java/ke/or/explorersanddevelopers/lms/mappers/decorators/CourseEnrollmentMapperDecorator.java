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

        // set the completed topics
        String completedTopicsIdsFromDb = courseEnrollment.getCompletedTopicsIds();
        List<String> completedTopicsIdsList = new ArrayList<>();

        if (completedTopicsIdsFromDb != null && !completedTopicsIdsFromDb.isEmpty() && !completedTopicsIdsFromDb.isBlank())
            completedTopicsIdsList.addAll(List.of(completedTopicsIdsFromDb.split(",")));

        Map<BigDecimal, Set<BigDecimal>> completedTopicsForResponse = new HashMap<>();
        courseEnrollment.getCourse().getTopics()
                .forEach(topic -> topic.getSubTopics()
                        .forEach(subTopic -> {
                            if (completedTopicsIdsList.contains(Integer.toString(subTopic.getSubTopicId().intValueExact())))
                                setSubTopicAsCompleted(completedTopicsForResponse, topic.getTopicId(), subTopic.getSubTopicId());
                        }));
        mappedCourseEnrollmentDto.setCompletedTopics(completedTopicsForResponse);

        return mappedCourseEnrollmentDto;
    }

    private void setSubTopicAsCompleted(Map<BigDecimal, Set<BigDecimal>> completedTopicsForResponse, BigDecimal topicId, BigDecimal subTopicId) {
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

        return mappedCourseEnrollment;
    }
}
