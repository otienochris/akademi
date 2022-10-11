package ke.or.explorersanddevelopers.lms.mappers;

import com.sun.xml.bind.v2.TODO;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
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

    @Override
    public CourseDto toDto(Course course) {
        CourseDto mappedCourseDto = courseMapper.toDto(course);
        //TODO: implement course mapper
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

        return mappedCourseDto;
    }

    @Override
    public Course toEntity(CourseDto courseDto) {
        Course mappedCourseEntity = courseMapper.toEntity(courseDto);

        return mappedCourseEntity;
    }
}
