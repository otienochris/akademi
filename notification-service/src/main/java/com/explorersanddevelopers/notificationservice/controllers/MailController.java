package com.explorersanddevelopers.notificationservice.controllers;

import com.explorersanddevelopers.notificationservice.models.CustomMimeMessage;
import com.explorersanddevelopers.notificationservice.models.SimpleMessage;
import com.explorersanddevelopers.notificationservice.responses.SentMimeMessage;
import com.explorersanddevelopers.notificationservice.responses.SentSimpleMailMessage;
import com.explorersanddevelopers.notificationservice.service.EmailService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 20/03/2022
 */

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;


//    @ApiOperation(value = "This end point accepts details of an email that lacks attachments and sends it to specified recipients")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully sent the mail."),
//            @ApiResponse(code = 400, message = "Bad request"),
//            @ApiResponse(code = 401, message = "You are unauthorized to access this resource"),
//            @ApiResponse(code = 403, message = "Performing this action is forbidden"),
//            @ApiResponse(code = 404, message = "Resource Not Found"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })
    @PostMapping(value = "/send-simple-message",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SentSimpleMailMessage> sendSimpleMail(@Validated SimpleMessage message) throws MessagingException {
        return new ResponseEntity<>(emailService.sendSimpleMessage(message), HttpStatus.OK);
    }

//    @ApiOperation(value = "This end point accepts details of an email that has attachments and sends it to specified recipients")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully sent the mail."),
//            @ApiResponse(code = 400, message = "Bad request"),
//            @ApiResponse(code = 401, message = "You are unauthorized to access this resource"),
//            @ApiResponse(code = 403, message = "Performing this action is forbidden"),
//            @ApiResponse(code = 404, message = "Resource Not Found"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })
    @PostMapping(
            value = "/send-mime-message",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SentMimeMessage> sendMimeMessage(@Validated CustomMimeMessage customMimeMessage) throws MessagingException, IOException {

        return new ResponseEntity<>(emailService.sendMimeMessage(customMimeMessage), HttpStatus.OK);
    }
}
