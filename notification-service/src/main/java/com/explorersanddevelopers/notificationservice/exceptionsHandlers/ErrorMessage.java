package com.explorersanddevelopers.notificationservice.exceptionsHandlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 20/03/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private Timestamp timestamp;
    private String message;
    private String details;
    private String cause;
}
