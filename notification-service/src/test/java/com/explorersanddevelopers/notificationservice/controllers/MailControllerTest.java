package com.explorersanddevelopers.notificationservice.controllers;

import com.explorersanddevelopers.notificationservice.models.CustomMimeMessage;
import com.explorersanddevelopers.notificationservice.models.SimpleMessage;
import com.explorersanddevelopers.notificationservice.responses.SentMimeMessage;
import com.explorersanddevelopers.notificationservice.responses.SentSimpleMailMessage;
import com.explorersanddevelopers.notificationservice.service.EmailServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atMostOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MailController.class)
@ActiveProfiles(profiles = {"dev", "common"})
class MailControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmailServiceImp emailService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void sendSimpleMail() throws Exception {
        // given
        var requestSimpleMessage = SimpleMessage.builder()
                .to("otienochris98@gmail.com,".split(","))
                .bcc(null)
                .cc(null)
                .subject("Test Subject")
                .text("Test Text")
                .build();
        var responseSimpleMessage = SentSimpleMailMessage.builder()
                .to("otienochris98@gmail.com,".split(","))
                .subject(requestSimpleMessage.getSubject())
                .text(requestSimpleMessage.getText())
                .bcc(null)
                .cc(null)
                .build();

        given(emailService.sendSimpleMessage(any(SimpleMessage.class))).willReturn(responseSimpleMessage);

        // when
        String result = mockMvc.perform(multipart("/mail/send-simple-message")
                        .param("to", requestSimpleMessage.getTo())
                        .param("subject", requestSimpleMessage.getSubject())
                        .param("text", requestSimpleMessage.getText()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // then
        then(emailService).should(atMostOnce()).sendSimpleMessage(any(SimpleMessage.class));
        assertThat(result).isEqualTo(objectMapper.writeValueAsString(responseSimpleMessage));
    }

    @Test
    void sendMimeMessage() throws Exception {

        // given
        MultipartFile multipartFile1 = new MockMultipartFile("TestFile1", "Content1".getBytes());
        MultipartFile multipartFile2 = new MockMultipartFile("TestFile2", "Content2".getBytes());
        MultipartFile multipartFile3 = new MockMultipartFile("TestFile3", "Content3".getBytes());
        MultipartFile[] attachments = {multipartFile1, multipartFile2, multipartFile3};
        var customMimeMessage = CustomMimeMessage.builder()
                .to("otienochris98@gmail.com,".split(","))
                .bcc(null)
                .cc("christopherochiengotieno@gmail.com".split(","))
                .subject("Test Subject")
                .text("Test Text")
                .attachments(attachments)
                .build();
        var sentMimeMessage = SentMimeMessage.builder()
                .to(customMimeMessage.getTo())
                .text(customMimeMessage.getText())
                .subject(customMimeMessage.getSubject())
                .cc(customMimeMessage.getCc())
                .bcc(customMimeMessage.getBcc())
                .attachments(new String[]{"TestFile1", "TestFile2", "TestFile3"})
                .build();
        given(emailService.sendMimeMessage(any(CustomMimeMessage.class))).willReturn(sentMimeMessage);

        // when
        String actualResponse = mockMvc.perform(multipart("/mail/send-mime-message")
                        .file("attachments", multipartFile1.getBytes())
                        .file("attachments", multipartFile2.getBytes())
                        .param("to", customMimeMessage.getTo())
                        .param("cc", customMimeMessage.getCc())
                        .param("subject", customMimeMessage.getSubject())
                        .param("text", customMimeMessage.getText()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // then
        then(emailService).should(atMostOnce()).sendMimeMessage(any());
        assertThat(actualResponse).isEqualTo(objectMapper.writeValueAsString(sentMimeMessage));

    }
}