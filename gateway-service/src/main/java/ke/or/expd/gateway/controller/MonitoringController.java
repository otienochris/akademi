package ke.or.expd.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {

    @GetMapping("/failure")
    public ResponseEntity<String> monitoring() {
        return ResponseEntity.ok("Monitoring is down");
    }
}
