package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, BigDecimal> {
    Boolean existsByCourseAndStudent(Course course, Student student);

    Optional<CourseEnrollment> getByCourseAndStudent(Course course, Student student);
}
