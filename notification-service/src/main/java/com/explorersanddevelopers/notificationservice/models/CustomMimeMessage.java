package com.explorersanddevelopers.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 20/03/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel(value = "Mime Message", description = "Mime Message details")
@JsonPropertyOrder(
        value = {"subject","to","cc","bcc","attachments","text"}
)
public class CustomMimeMessage {

//    @ApiModelProperty(name = "to", example = "abc@xyz.com", notes = "Recipient(s)' email(s).")
    @NotNull
    private String[] to;
//    @ApiModelProperty(name = "cc", example = "xyz@abc.org", notes = "Carbon Copy recipient(s).")
    private String[] cc;
//    @ApiModelProperty(name = "bcc", example = "jkl@lkj.tech", notes = "Blind Carbon Copy recipient(s)")
    private String[] bcc;
    @NotNull
//    @ApiModelProperty(name = "subject", example = "Job Application", notes = "The email's subject")
    private String subject;
    @NotNull
//    @ApiModelProperty(name = "text", example = "I write to apply for the ...", notes = "The email's body/content.")
    private String text;
    @NotNull
//    @ApiModelProperty(name = "attachments", dataType = "MultipartFiles",example = "cv.txt", notes = "The email's attachment(s).")
    private MultipartFile[] attachments;
}
