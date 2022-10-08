package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 08/10/2022
 */
public interface AddressRepository extends JpaRepository<Address, BigDecimal> {
}
