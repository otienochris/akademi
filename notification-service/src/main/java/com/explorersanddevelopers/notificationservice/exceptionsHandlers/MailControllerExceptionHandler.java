package com.explorersanddevelopers.notificationservice.exceptionsHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 20/03/2022
 */

@ControllerAdvice
public class MailControllerExceptionHandler {
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorMessage> handleMessagingException(MessagingException e) {
        return new ResponseEntity<>(generateErrorMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set<ConstraintViolation<?>>> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getConstraintViolations(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorMessage generateErrorMessage(Exception e) {
        return ErrorMessage.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .message(e.getMessage())
                .cause(e.getCause().toString())
                .details(Arrays.toString(e.getStackTrace()))
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOtherException(Exception exception) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .message(exception.getMessage())
                .details(Arrays.toString(exception.getStackTrace()))
                .build();
        if (exception.getCause() != null)
            errorMessage.setCause(exception.getCause().toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
