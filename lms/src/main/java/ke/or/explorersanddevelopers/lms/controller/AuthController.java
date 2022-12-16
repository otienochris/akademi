package ke.or.explorersanddevelopers.lms.controller;

import ke.or.explorersanddevelopers.lms.model.security.AuthenticationRequestDto;
import ke.or.explorersanddevelopers.lms.model.security.AuthenticationResponse;
import ke.or.explorersanddevelopers.lms.service.UserService;
import ke.or.explorersanddevelopers.lms.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody AuthenticationRequestDto user) {
        HashMap<String, Object> error = new HashMap<>();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Incorrect username and password");
        } catch (DisabledException e) {
            error.put("message: ", e.getMessage());
            error.put("possible_solution: ", new String[]{"Ensure your email is verified", "Contact your Admin for help"});
            error.put("Time: ", LocalTime.now().toString());
            error.put("Token: ", getJwtToken(user));
            return ResponseEntity.badRequest().body(error);
        }

        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(getJwtToken(user)).build());
    }

    private String getJwtToken(AuthenticationRequestDto user) {
        return jwtUtil.generateToken(userDetailsService.loadUserByUsername(user.getUsername()));
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
