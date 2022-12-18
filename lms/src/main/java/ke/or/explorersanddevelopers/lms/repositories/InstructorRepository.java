package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 08/10/2022
 */
public interface InstructorRepository extends JpaRepository<Instructor, BigDecimal> {
    Optional<Instructor> getByInstructorId(BigDecimal instructorId);

    Optional<Instructor> findByEmail(String email);
}
