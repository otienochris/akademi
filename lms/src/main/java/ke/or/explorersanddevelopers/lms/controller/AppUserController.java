package ke.or.explorersanddevelopers.lms.controller;

import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import ke.or.explorersanddevelopers.lms.model.security.AuthenticationResponse;
import ke.or.explorersanddevelopers.lms.model.security.Role;
import ke.or.explorersanddevelopers.lms.repositories.AppUserRepository;
import ke.or.explorersanddevelopers.lms.service.UserService;
import ke.or.explorersanddevelopers.lms.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static ke.or.explorersanddevelopers.lms.controller.AuthController.getRole;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {
    private final AppUserRepository appUserRepository;

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(userService.getUsers(PageRequest.of(pageNo, pageSize)));
    }

    @PostMapping
    public ResponseEntity<AppUser> saveUser(@RequestBody @Validated AppUser appUser) {
        AppUser user = userService.saveUser(appUser);
        String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + user.getId()).toUriString();
        URI location = URI.create(uriString);
        return ResponseEntity.created(location).body(user);
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody @Validated Role role) {
        Role savedRole = userService.saveRole(role);
        String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + savedRole.getId()).toUriString();
        URI location = URI.create(uriString);
        return ResponseEntity.created(location).body(savedRole);
    }

    @GetMapping("/{username}/add-role/{roleName}")
    public ResponseEntity<?> addRoleToUser(@PathVariable String username, @PathVariable String roleName) {
        userService.addRoleToUser(username, roleName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam String email,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        AuthenticationResponse authenticationResponse = null;

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, oldPassword));

            AppUser user = getAppUser(email);
            user.setPassword(passwordEncoder.encode(newPassword));
            appUserRepository.save(user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = getJwtToken(userDetails);
            Date date = jwtUtil.extractExpiration(token);
            String authority = userDetails.getAuthorities().stream().findFirst().orElseThrow(() -> {
                throw new IllegalStateException("User cannot exist without at least one role");
            }).getAuthority();

            authenticationResponse = AuthenticationResponse.builder()
                    .token(token)
                    .expiry(date)
                    .role(getRole(authority))
                    .build();

        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Incorrect username and password");
        } catch (DisabledException e) {
            throw new UsernameNotFoundException("User is disabled");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(authenticationResponse);
    }

    private String getJwtToken(UserDetails user) {
        return jwtUtil.generateToken(user);
    }

    private AppUser getAppUser(String email) {
        return appUserRepository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
