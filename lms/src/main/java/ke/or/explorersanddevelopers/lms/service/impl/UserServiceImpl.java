package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.exception.RecordNotFoundException;
import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import ke.or.explorersanddevelopers.lms.model.security.Role;
import ke.or.explorersanddevelopers.lms.repositories.AppUserRepository;
import ke.or.explorersanddevelopers.lms.repositories.RoleRepository;
import ke.or.explorersanddevelopers.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user to the database");
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        userRepository.findByUsername(username).ifPresentOrElse(user ->
                roleRepository.findByName(roleName).ifPresentOrElse(role -> {
                    user.getRoles().add(role);
                    userRepository.save(user);
                }, () -> {
                    throw new RecordNotFoundException("Role does not exists");
                }), () -> {
            throw new UsernameNotFoundException("User not found");
        });
    }

    @Override
    public AppUser getUserByUsername(String username) {
        log.info("Retrieving user {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public List<AppUser> getUsers(Pageable pageable) {
        log.info("Retrieving a list of users");
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean verifyEmail(String token) {
        userRepository.findByEmailVerificationCode(token).ifPresentOrElse(user -> {
            user.setAccountDisabled(false);
            user.setEmailVerificationCode(null);
            userRepository.save(user);
        }, () -> {
            throw new NoSuchElementException("Invalid verification token!");
        });
        return true;
    }

}
