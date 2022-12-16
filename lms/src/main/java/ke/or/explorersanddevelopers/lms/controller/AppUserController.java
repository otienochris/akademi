package ke.or.explorersanddevelopers.lms.controller;

import ke.or.explorersanddevelopers.lms.model.security.AppUser;
import ke.or.explorersanddevelopers.lms.model.security.Role;
import ke.or.explorersanddevelopers.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final UserService userService;

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
}
