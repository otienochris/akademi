package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.enums.YesOrNoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Answer Dto", description = "Answer Dto details")
public class AnswerDto implements Serializable {

    private static final long serialVersionUID = 6174316311408525731L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Answer record id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private UUID answerId;

    @NotNull
    @ApiModelProperty(example = "The truth is in the correct answer.", notes = "The actual answer content.")
    private String content;

    @NotNull
    @ApiModelProperty(example = "Y", notes = "Is the answer the correct answer.")
    private YesOrNoEnum isCorrect;

    @ApiModelProperty(example = "It is true cause it is true.", notes = "The reason why the answer is right or wrong.")
    private String reason;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2022-02-03", notes = "Address Record creation date.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Date creationDate;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2022-02-03", notes = "Address record modification date.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Date modificationDate;

    @NotNull
    @ApiModelProperty(example = "0", notes = "Address record version.")
    private Long version;
}
