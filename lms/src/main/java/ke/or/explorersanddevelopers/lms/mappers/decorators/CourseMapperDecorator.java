package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.*;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
public class CourseMapperDecorator implements CourseMapper {

    @Autowired
    @Qualifier("delegate")
    private CourseMapper courseMapper;

    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    @Qualifier("delegate")
    private CourseEnrollmentMapper courseEnrollmentMapper;

    @Autowired
    @Qualifier("delegate")
    private TopicMapper topicMapper;

    @Autowired
    @Qualifier("delegate")
    private InstructorMapper instructorMapper;

    @Override
    public CourseDto toDto(Course course) {
        CourseDto mappedCourseDto = courseMapper.toDto(course);

        mappedCourseDto.setReviews(new HashSet<>());
        Set<Review> reviews = course.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            reviews.forEach(review -> mappedCourseDto.getReviews().add(reviewMapper.toDto(review)));
        }

        mappedCourseDto.setCourseEnrollments(new HashSet<>());
        Set<CourseEnrollment> courseEnrollments = course.getCourseEnrollments();
        if (courseEnrollments != null && !courseEnrollments.isEmpty()) {
            courseEnrollments.forEach(courseEnrollment -> mappedCourseDto.getCourseEnrollments().add(courseEnrollmentMapper.toDto(courseEnrollment)));
        }

        mappedCourseDto.setTopics(new HashSet<>());
        Set<Topic> topics = course.getTopics();
        if (topics != null && !topics.isEmpty()) {
            topics.forEach(topic -> mappedCourseDto.getTopics().add(topicMapper.toDto(topic)));
        }

        mappedCourseDto.setInstructors(new HashSet<>());
        Set<Instructor> instructors = course.getInstructors();
        if (instructors != null && !instructors.isEmpty()) {
            instructors.forEach(instructor -> mappedCourseDto.getInstructors().add(instructorMapper.toDto(instructor)));
        }

        return mappedCourseDto;
    }

    @Override
    public Course toEntity(CourseDto courseDto) {
        Course mappedCourseEntity = courseMapper.toEntity(courseDto);

        mappedCourseEntity.setReviews(new HashSet<>());
        Set<ReviewDto> reviews = courseDto.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            reviews.forEach(reviewDto -> mappedCourseEntity.getReviews().add(reviewMapper.toEntity(reviewDto)));
        }

        mappedCourseEntity.setCourseEnrollments(new HashSet<>());
        Set<CourseEnrollmentDto> courseEnrollments = courseDto.getCourseEnrollments();
        if (courseEnrollments != null && !courseEnrollments.isEmpty()) {
            courseEnrollments.forEach(courseEnrollmentDto -> mappedCourseEntity.getCourseEnrollments().add(courseEnrollmentMapper.toEntity(courseEnrollmentDto)));
        }

        mappedCourseEntity.setTopics(new HashSet<>());
        Set<TopicDto> topics = courseDto.getTopics();
        if (topics != null && !topics.isEmpty()) {
            topics.forEach(topicDto -> mappedCourseEntity.getTopics().add(topicMapper.toEntity(topicDto)));
        }

        mappedCourseEntity.setInstructors(new HashSet<>());
        Set<InstructorDto> instructors = courseDto.getInstructors();
        if (instructors != null && !instructors.isEmpty()) {
            instructors.forEach(instructorDto -> mappedCourseEntity.getInstructors().add(instructorMapper.toEntity(instructorDto)));
        }

        return mappedCourseEntity;
    }
}
