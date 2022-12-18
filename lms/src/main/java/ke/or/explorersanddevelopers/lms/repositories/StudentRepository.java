package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */
public interface StudentRepository extends JpaRepository<Student, BigDecimal> {
    Optional<Student> getByStudentId(BigDecimal studentId);

    Optional<Student> getByEmail(String email);

    Optional<Student> findByEmail(String email);
}
