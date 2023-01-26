package com.explorersanddevelopers.notificationservice.service;

import com.explorersanddevelopers.notificationservice.models.CustomMimeMessage;
import com.explorersanddevelopers.notificationservice.models.SimpleMessage;
import com.explorersanddevelopers.notificationservice.responses.SentMimeMessage;
import com.explorersanddevelopers.notificationservice.responses.SentSimpleMailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 20/03/2022
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImp implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public SentSimpleMailMessage sendSimpleMessage(SimpleMessage message) throws MessagingException {
        log.info("Sending simple email to: " + Arrays.toString(message.getTo()) + " with the subject: [" + message.getSubject() + "]");

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setTo(message.getTo());

        String[] cc = message.getCc();
        if (cc != null)
            helper.setCc(cc);

        String[] bcc = message.getBcc();
        if (bcc != null)
            helper.setBcc(bcc);

        helper.setSubject(message.getSubject().toUpperCase());
        helper.setText(message.getText(), true);
        emailSender.send(mimeMessage);

        log.info("Email Sent Successfully");
        return SentSimpleMailMessage.builder()
                .bcc(bcc)
                .cc(cc)
                .subject(message.getSubject())
                .text(message.getText())
                .to(message.getTo())
                .build();
    }

    @Override
    public SentMimeMessage sendMimeMessage(CustomMimeMessage message) throws MessagingException {
        log.info("Sending mime email to: [" + Arrays.toString(message.getTo()) + "] with the subject: [" + message.getSubject() + "]");

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(message.getTo());

        String[] cc = message.getCc();
        String[] bcc = message.getBcc();
        if (cc != null)
            helper.setCc(cc);
        if (bcc != null)
            helper.setBcc(bcc);

        helper.setSubject(message.getSubject().toUpperCase());
        helper.setText(message.getText(), true);

        // add attachmentsFileNames
        MultipartFile[] multipartFiles = message.getAttachments();
        if (multipartFiles.length > 0) {
            Arrays.stream(multipartFiles).forEach(multipartFile -> {
                try {
                    helper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), new ByteArrayResource(multipartFile.getBytes()));
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // send the final message
        emailSender.send(mimeMessage);
        log.info("Email Sent Successfully");

        // attachment names for the response object
        String[] attachmentsFileNames = new String[multipartFiles.length];
        for (int i = 0; i < multipartFiles.length; i++) {
            attachmentsFileNames[i] = multipartFiles[i].getOriginalFilename();
        }

        return SentMimeMessage.builder()
                .attachments(attachmentsFileNames)
                .bcc(bcc)
                .cc(cc)
                .subject(message.getSubject())
                .text(message.getText())
                .to(message.getTo())
                .build();
    }


}
