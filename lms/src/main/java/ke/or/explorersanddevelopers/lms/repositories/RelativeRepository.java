package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.entity.Relative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 09/10/2022
 */
public interface RelativeRepository extends JpaRepository<Relative, BigDecimal> {
    Optional<Relative> getByRelativeId(BigDecimal relativeId);
}
