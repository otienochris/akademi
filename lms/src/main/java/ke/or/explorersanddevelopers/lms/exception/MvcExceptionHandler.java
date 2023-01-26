package ke.or.explorersanddevelopers.lms.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Thursday, 06/10/2022
 */

@Slf4j
@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationExceptionHandler(ConstraintViolationException e) {

        log.error("in the validationExceptionHandler");
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(constraintViolation -> errors.add(constraintViolation.toString()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ObjectError>> bindingExceptionHandler(BindException exception) {
        log.error("in the bindingExceptionHandler");
        return new ResponseEntity<>(exception.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<ErrorDetails> itemNotFoundExceptionHandler(NoSuchFileException e) {
        return new ResponseEntity<>(createErrorDetails(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchRecordException.class)
    public ResponseEntity<ErrorDetails> noSuchRecordException(NoSuchRecordException e) {
        return new ResponseEntity<>(createErrorDetails(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(createErrorDetails(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateKeyException(DuplicateKeyException e) {
        return new ResponseEntity<>(createErrorDetails(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDetails> handleNullPointerException(NullPointerException e) {
        return new ResponseEntity<>(createErrorDetails(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<ErrorDetails> handleNullPointerException(UserDisabledException e) {
        return new ResponseEntity<>(createErrorDetails(e), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNullPointerException(UsernameNotFoundException e) {

        return new ResponseEntity<>(createErrorDetails(e), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneralException(Exception e) {

        if (e.getMessage() == null && e.getCause() == null) {
            return new ResponseEntity<>(ErrorDetails.builder()
                    .timeStamp(LocalDateTime.now())
                    .details("")
                    .message("")
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }
        if (e.getMessage() == null) {
            return new ResponseEntity<>(ErrorDetails.builder()
                    .timeStamp(LocalDateTime.now())
                    .details(e.getCause().toString())
                    .message("")
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }
        if (e.getCause() == null) {
            return new ResponseEntity<>(ErrorDetails.builder()
                    .timeStamp(LocalDateTime.now())
                    .details("")
                    .message(e.getMessage())
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .details(e.getCause().toString())
                .message(e.getMessage())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    private ErrorDetails createErrorDetails(Exception e) {
        return ErrorDetails.builder()
                .details(Arrays.toString(e.getStackTrace()))
                .message(e.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
