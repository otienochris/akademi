package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Friday 07/10/2022
 **/
public interface CourseService {

    /**
     * This method creates a course record
     *
     * @param courseDto - the course details required to create a course
     * @return returns the created course
     */

    CourseDto createNewCourse(BigDecimal instructorId, CourseDto courseDto);

    /**
     * This method retrieves a course by course record id
     *
     * @param courseId - the course id required to retrieve a course
     * @return - returns the retrieved course record
     */

    CourseDto getCourseByCode(BigDecimal courseId);

    /**
     * This method deletes a course record by the course id
     *
     * @param courseId - the course record required to delete  a course
     * @return true if a course was successfully deleted
     */
    Boolean deleteCourseByCode(BigDecimal courseId);

    /**
     * This method returns a list of courses
     *
     * @param pageable - a page object of course with useful information such as total pages
     * @return - returns  list of courses
     */

    List<CourseDto> getListOfCourses(Pageable pageable);

    /**
     * This method allows a user to edit course details using record course id
     *
     * @param courseId  - the course required to retrieve a course for editing
     * @param courseDto - the course details to be edited
     * @return - returns the edited course records
     */
    CourseDto editCourseByCode(BigDecimal courseId, CourseDto courseDto);


    /**
     * This method returns a list of topics by course id
     *
     * @return - returns  list of topics
     */
    List<TopicDto> getCourseTopicsByCourseCode(BigDecimal courseId);
}
