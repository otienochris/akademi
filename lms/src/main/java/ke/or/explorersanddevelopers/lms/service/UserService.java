package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import ke.or.explorersanddevelopers.lms.model.security.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    AppUser getUserByUsername(String username);

    List<AppUser> getUsers(Pageable pageable);

    boolean verifyEmail(String token);
}
