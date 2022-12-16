package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, BigDecimal> {

    Optional<AppUser> findByUsername(String username);


    Optional<AppUser> getByToken(String userToken);

    Optional<AppUser> findByEmailVerificationCode(String token);
}
