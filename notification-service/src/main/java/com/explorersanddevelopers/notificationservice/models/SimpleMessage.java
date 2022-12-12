package com.explorersanddevelopers.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@JsonPropertyOrder(
        value = {"subject","to","cc","bcc","text"}
)
//@ApiModel(value = "Simple Message", description = "Simple Message Details")
public class SimpleMessage {
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
}
