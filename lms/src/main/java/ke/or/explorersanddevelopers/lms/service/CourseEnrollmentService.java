package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
public interface CourseEnrollmentService {

    /**
     * This method retrieves a course enrollment record by the provided student id
     *
     * @param studentId - the student id to be used to search for the course enrollment
     * @return a list of course enrollments associated with a student
     */
    List<CourseEnrollmentDto> getCourseEnrollmentByStudentId(BigDecimal studentId);

    /**
     * This method enroll a student to a course
     *
     * @param studentId - the id of the student to be enrolled on a course
     * @param courseId  - the id of the course the student is to be enrolled to
     * @return the course enrollment record
     */
    CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId);

    /**
     * This method retrieve a paged list of course enrollments
     *
     * @param pageable paging object containing details like page number and page size
     */
    List<CourseEnrollmentDto> getCourseEnrollments(Pageable pageable);

    /**
     * This method allows a student to submit a completed topic
     *
     * @param courseEnrollmentId - the id of the course enrollment detail to be updatec
     * @param subTopicId         - the id of the subTopic being submitted
     * @return the updated course enrollment record
     */
    CourseEnrollmentDto completeSubTopic(BigDecimal courseEnrollmentId, BigDecimal subTopicId);
}
