package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.enums.AnswerTypeEnum;
import ke.or.explorersanddevelopers.lms.enums.DifficultyLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Question Dto", description = "Question Dto details")
public class QuestionDto implements Serializable {

    private static final long serialVersionUID = 3487725639797483723L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Question record id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private BigDecimal questionId;

    @NotNull
    @ApiModelProperty(example = "Are you okay?", notes = "The actual question")
    private String question;

    @ApiModelProperty(example = "Feel your head's temperature", notes = "An hint to getting the question right.")
    private String hint;

    @NotNull
    @ApiModelProperty(example = "EASY", notes = "Question difficulty level.")
    private DifficultyLevelEnum level;

    @ApiModelProperty(example = "10", notes = "The rate at which people get this question right.")
    private Double successRate;

    @ApiModelProperty(example = "3", notes = "The period, in minutes, to which the question must have been answered.")
    private Long period;

    @NotNull
    @ApiModelProperty(example = "SINGLE_CHOICE", notes = "Expected Answer type")
    private AnswerTypeEnum answerType;

    @ApiModelProperty(notes = "A list of answers")
    private List<AnswerDto> answers;

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
