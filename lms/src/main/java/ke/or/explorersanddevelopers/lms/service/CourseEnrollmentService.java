package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
public interface CourseEnrollmentService {
    List<CourseEnrollmentDto> getCourseEnrollmentByStudentId(BigDecimal studentId);
    CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId);

    List<CourseEnrollmentDto> getCourseEnrollments(Pageable pageable);
}
