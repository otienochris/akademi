package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
public interface CourseEnrollmentService {
    List<CourseEnrollmentDto> getCourseEnrollmentByStudentId(BigDecimal studentId);

    CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId);

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
