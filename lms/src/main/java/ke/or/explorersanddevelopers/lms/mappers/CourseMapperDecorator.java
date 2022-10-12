package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.mapper.InstructorMapper;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

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

        mappedCourseDto.setReviews(new ArrayList<>());
        List<Review> reviews = course.getReviews();
        if (reviews != null && reviews.size() > 0) {
            reviews.forEach(review -> mappedCourseDto.getReviews().add(reviewMapper.toDto(review)));
        }

        mappedCourseDto.setCourseEnrollments(new ArrayList<>());
        List<CourseEnrollment> courseEnrollments = course.getCourseEnrollments();
        if (courseEnrollments != null && courseEnrollments.size() > 0) {
            courseEnrollments.forEach(courseEnrollment -> mappedCourseDto.getCourseEnrollments().add(courseEnrollmentMapper.toDto(courseEnrollment)));
        }

        mappedCourseDto.setTopics(new ArrayList<>());
        List<Topic> topics = course.getTopics();
        if (topics != null && topics.size() > 0) {
            topics.forEach(topic -> mappedCourseDto.getTopics().add(topicMapper.toDto(topic)));
        }

        mappedCourseDto.setInstructors(new ArrayList<>());
        List<Instructor> instructors = course.getInstructors();
        if (instructors != null && instructors.size() > 0) {
            instructors.forEach(instructor -> mappedCourseDto.getInstructors().add(instructorMapper.toDto(instructor)));
        }

        return mappedCourseDto;
    }

    @Override
    public Course toEntity(CourseDto courseDto) {
        Course mappedCourseEntity = courseMapper.toEntity(courseDto);

        mappedCourseEntity.setReviews(new ArrayList<>());
        List<ReviewDto> reviews = courseDto.getReviews();
        if (reviews != null && reviews.size() > 0) {
            reviews.forEach(reviewDto -> mappedCourseEntity.getReviews().add(reviewMapper.toEntity(reviewDto)));
        }

        mappedCourseEntity.setCourseEnrollments(new ArrayList<>());
        List<CourseEnrollmentDto> courseEnrollments = courseDto.getCourseEnrollments();
        if (courseEnrollments != null && courseEnrollments.size() > 0) {
            courseEnrollments.forEach(courseEnrollmentDto -> mappedCourseEntity.getCourseEnrollments().add(courseEnrollmentMapper.toEntity(courseEnrollmentDto)));
        }

        mappedCourseEntity.setTopics(new ArrayList<>());
        List<TopicDto> topics = courseDto.getTopics();
        if (topics != null && topics.size() > 0) {
            topics.forEach(topicDto -> mappedCourseEntity.getTopics().add(topicMapper.toEntity(topicDto)));
        }

        mappedCourseEntity.setInstructors(new ArrayList<>());
        List<InstructorDto> instructors = courseDto.getInstructors();
        if (instructors != null && instructors.size() > 0) {
            instructors.forEach(instructorDto -> mappedCourseEntity.getInstructors().add(instructorMapper.toEntity(instructorDto)));
        }

        return mappedCourseEntity;
    }
}
