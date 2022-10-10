package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Question Dto", description = "Question Dto details")
public class QuestionDto implements Serializable {

    private static final long serialVersionUID = 3487725639797483723L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Question record id", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal questionId;

    @NotNull
    @Schema(example = "Are you okay?", description = "The actual question")
    private String question;

    @Schema(example = "Feel your head's temperature", description = "An hint to getting the question right.")
    private String hint;

    @NotNull
    @Schema(example = "EASY", description = "Question difficulty level.")
    private DifficultyLevelEnum level;

    @Schema(example = "10", description = "The rate at which people get this question right.")
    private Double successRate;

    @Schema(example = "3", description = "The period, in minutes, to which the question must have been answered.")
    private Long period;

    @NotNull
    @Schema(example = "SINGLE_CHOICE", description = "Expected Answer type")
    private AnswerTypeEnum answerType;

    @Schema(description = "A list of answers")
    private List<AnswerDto> answers;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "2022-02-03", description = "Address Record creation date.", accessMode = Schema.AccessMode.READ_ONLY)
    private Date creationDate;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "2022-02-03", description = "Address record modification date.", accessMode = Schema.AccessMode.READ_ONLY)
    private Date modificationDate;

    @NotNull
    @Schema(example = "0", description = "Address record version.")
    private Long version;
}
