package ke.or.explorersanddevelopers.lms.controller;

import ke.or.explorersanddevelopers.lms.enums.RolesEnum;
import ke.or.explorersanddevelopers.lms.model.security.AuthenticationRequestDto;
import ke.or.explorersanddevelopers.lms.model.security.AuthenticationResponse;
import ke.or.explorersanddevelopers.lms.repositories.AppUserRepository;
import ke.or.explorersanddevelopers.lms.service.UserService;
import ke.or.explorersanddevelopers.lms.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Incorrect username and password");
        } catch (DisabledException e) {
            throw new UsernameNotFoundException("User is disabled");
        } catch (Exception e){
            e.printStackTrace();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = getJwtToken(userDetails);
        Date date = jwtUtil.extractExpiration(token);
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

    private RolesEnum getRole(String s) {
        switch (s){
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
