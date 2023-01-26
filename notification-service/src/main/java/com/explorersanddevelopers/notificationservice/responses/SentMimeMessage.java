package com.explorersanddevelopers.notificationservice.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 20/03/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SentMimeMessage {
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String text;
    private String[] attachments;
}
