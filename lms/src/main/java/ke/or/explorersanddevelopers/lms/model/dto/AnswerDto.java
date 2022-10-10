package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import ke.or.explorersanddevelopers.lms.enums.YesOrNoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Answer Dto", description = "Answer Dto details")
public class AnswerDto implements Serializable {

    private static final long serialVersionUID = 6174316311408525731L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Answer record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal answerId;

    @NotNull
    @Schema(example = "The truth is in the correct answer.", description = "The actual answer content.")
    private String content;

    @NotNull
    @Schema(example = "Y", description = "Is the answer the correct answer.")
    private YesOrNoEnum isCorrect;

    @Schema(example = "It is true cause it is true.", description = "The reason why the answer is right or wrong.")
    private String reason;

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
