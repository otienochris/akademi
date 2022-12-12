package com.explorersanddevelopers.notificationservice.service;

import com.explorersanddevelopers.notificationservice.models.CustomMimeMessage;
import com.explorersanddevelopers.notificationservice.models.SimpleMessage;
import com.explorersanddevelopers.notificationservice.responses.SentMimeMessage;
import com.explorersanddevelopers.notificationservice.responses.SentSimpleMailMessage;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 20/03/2022
 */

public interface EmailService {
    /**
     * This method sends a simple mail message
     *
     * @param message - the message object
     * @return sent simple message
     */
    SentSimpleMailMessage sendSimpleMessage(SimpleMessage message) throws MessagingException;

    /**
     * This method sends a mime mail message, one that has attachments
     *
     * @param customMimeMessage - message object
     * @return sent mime message
     */
    SentMimeMessage sendMimeMessage(CustomMimeMessage customMimeMessage) throws MessagingException, IOException;
}
