package ke.or.expd.authserver.repositories;

import ke.or.expd.authserver.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigDecimal> {
    Optional<User> findByEmail(String email);
}
