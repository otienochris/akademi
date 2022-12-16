package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, BigDecimal> {
    Optional<Role> findByName(String role);
}
