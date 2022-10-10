package ke.or.explorersanddevelopers.lms.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Thursday, 06/10/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String message;
    private String details;
    private LocalDateTime timeStamp;
}
