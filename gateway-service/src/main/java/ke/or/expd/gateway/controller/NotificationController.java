package ke.or.expd.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @GetMapping("/failure")
    public ResponseEntity<String> monitoring() {
        return ResponseEntity.ok("Notification is down");
    }
}
