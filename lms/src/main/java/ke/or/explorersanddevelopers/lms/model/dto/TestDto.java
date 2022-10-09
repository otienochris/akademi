package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import ke.or.explorersanddevelopers.lms.enums.TestTypeEnum;
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
@Schema(name = "Test Dto", description = "Test Dto details")
public class TestDto implements Serializable {

    private static final long serialVersionUID = -878009969108979322L;
    @Null
    @Schema(example = "101", description = "Test record id", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal testId;

    @Schema(description = "topics")
    private List<TopicDto> topics;

    @NotNull
    @Schema(example = "EXAM", description = "The type of test.")
    private TestTypeEnum testType;

    @NotNull
    @Schema(example = "N", description = "Is the test optional.")
    private YesOrNoEnum isOptional;

    @NotNull
    @Schema(example = "Y", description = "Is the test scheduled.")
    private YesOrNoEnum isScheduled;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "2022-01-01", description = "The date the test starts")
    private Date startDateAndTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "2022-02-01", description = "The date the test ends.")
    private Date endDateAndTime;

    @Schema(description = "Questions within the test.")
    private List<QuestionDto> questions;

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
