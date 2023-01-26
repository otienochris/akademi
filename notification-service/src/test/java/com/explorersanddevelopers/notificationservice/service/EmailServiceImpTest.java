package com.explorersanddevelopers.notificationservice.service;

import com.explorersanddevelopers.notificationservice.models.CustomMimeMessage;
import com.explorersanddevelopers.notificationservice.models.SimpleMessage;
import com.explorersanddevelopers.notificationservice.responses.SentMimeMessage;
import com.explorersanddevelopers.notificationservice.responses.SentSimpleMailMessage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Email Service Implementation Test")
class EmailServiceImpTest {

    @Mock
    private JavaMailSenderImpl emailSender;

    @InjectMocks
    private EmailServiceImp emailServiceImp;

    @Test
    @Disabled
    @DisplayName("Send Simple Message")
    void sendSimpleMessage() throws MessagingException {
        given(emailSender.createMimeMessage()).willReturn(any(MimeMessage.class));

        var actualResponse = emailServiceImp.sendSimpleMessage(SimpleMessage.builder()
                .text("Test Text")
                .subject("Test Subject")
                .to("otienochris98@gmail.com".split(","))
                .cc(null)
                .bcc(null)
                .build());

        assertThat(actualResponse).isEqualTo(SentSimpleMailMessage.builder()
                .cc(null)
                .bcc(null)
                .text("Test Text")
                .subject("Test Subject")
                .to("otienochris98@gmail.com".split(","))
                .build());
    }

    @Test
    @DisplayName("Send Mime Message")
    void sendMimeMessage() throws MessagingException {
        MultipartFile multipartFile1 = new MockMultipartFile("TestFile1", "TestFile1", null, "Content1".getBytes());
        MultipartFile multipartFile2 = new MockMultipartFile("TestFile2", "TestFile2", null, "Content2".getBytes());
        MultipartFile multipartFile3 = new MockMultipartFile("TestFile3", "TestFile3", null, "Content3".getBytes());
        MultipartFile[] attachments = {multipartFile1, multipartFile2, multipartFile3};
        CustomMimeMessage customMimeMessage = CustomMimeMessage.builder()
                .attachments(attachments)
                .cc(null)
                .to("otienochris98@gmail.com".split(","))
                .text("Test Text")
                .subject("Test Subject")
                .bcc(null)
                .build();
        var expectedResponse = SentMimeMessage.builder()
                .to(customMimeMessage.getTo())
                .text(customMimeMessage.getText())
                .subject(customMimeMessage.getSubject())
                .cc(customMimeMessage.getCc())
                .bcc(customMimeMessage.getBcc())
                .attachments(new String[]{"TestFile1", "TestFile2", "TestFile3"})
                .build();

        when(emailSender.createMimeMessage()).thenCallRealMethod();

        // when
        SentMimeMessage actualResponse = emailServiceImp.sendMimeMessage(customMimeMessage);

        // then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}