package ke.or.explorersanddevelopers.lms.controller;

import ke.or.explorersanddevelopers.lms.enums.RolesEnum;
import ke.or.explorersanddevelopers.lms.model.security.AuthenticationRequestDto;
import ke.or.explorersanddevelopers.lms.model.security.AuthenticationResponse;
import ke.or.explorersanddevelopers.lms.repositories.AppUserRepository;
import ke.or.explorersanddevelopers.lms.service.UserService;
import ke.or.explorersanddevelopers.lms.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody AuthenticationRequestDto user) {
        String username = user.getUsername();
        try {
            log.info("Authenticating {}", username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
        } catch (BadCredentialsException e) {
            String errorMessage = "Incorrect username and password";
            log.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        } catch (DisabledException e) {
            String errorMessage = "User is disabled";
            log.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        } catch (Exception e) {
            log.error("Error occurred while trying to authenticate {}", username);
            throw new RuntimeException(e);
        }

        log.info("{} authenticated successfully", username);
        log.info("Loading user details by username {}" + username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String token = getJwtToken(userDetails);
        Date date = jwtUtil.extractExpiration(token);
        log.info("Generated token will expire on {}", date.toString());

        String authority = userDetails.getAuthorities().stream().findFirst().orElseThrow(() -> {
            throw new IllegalStateException("User cannot exist without at least one role");
        }).getAuthority();

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(token)
                .expiry(date)
                .role(getRole(authority))
                .build();

        return ResponseEntity.ok(authenticationResponse);
    }

    public static RolesEnum getRole(String s) {
        switch (s) {
            case "ROLE_INSTRUCTOR":
                return RolesEnum.ROLE_INSTRUCTOR;
            case "ROLE_STUDENT":
                return RolesEnum.ROLE_STUDENT;
            case "ROLE_USER":
                return RolesEnum.ROLE_USER;
            case "ROLE_ADMIN":
                return RolesEnum.ROLE_ADMIN;
            case "ROLE_SUPER_ADMIN":
                return RolesEnum.ROLE_SUPER_ADMIN;
            case "ROLE_RELATIVE":
                return RolesEnum.ROLE_RELATIVE;
            default:
                throw new IllegalStateException("Unknown role");
        }
    }

    private String getJwtToken(UserDetails user) {
        log.info("Generating jwt token");
        return jwtUtil.generateToken(user);
    }

    @GetMapping("/verifyEmail/{emailVerificationToken}")
    public ResponseEntity<?> verifyEmail(@PathVariable("emailVerificationToken") String token) {
        boolean isVerified = userService.verifyEmail(token);

        Map<String, Object> message = new HashMap<>();
        if (isVerified) {
            message.put("status", "success");
            message.put("message", "Email Verified successfully");
        } else {
            message.put("status", "Failed");
            message.put("message", "Email Could not be verified successfully");
        }
        return isVerified ? ResponseEntity.ok(message) : ResponseEntity.badRequest().body(message);
    }
}
