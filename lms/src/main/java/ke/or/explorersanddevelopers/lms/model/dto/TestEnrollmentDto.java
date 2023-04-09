package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Test Enrollment Dto", description = "Test Enrollment Dto details")
public class TestEnrollmentDto implements Serializable {

    private static final long serialVersionUID = -5163497779278050798L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Test Enrollment Record Id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal testEnrollmentId;

    @NotNull
    @Schema(example = "COMPLETED", description = "Test enrollment status.")
    private StatusEnum status;

    @Schema(example = "2022-01-02", description = "The date all the tests were completed.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completionDate;

    @Schema(example = "20", description = "The amount the student paid when enrolling for the tests if any.")
    private BigDecimal amount;

    @Schema(example = "20", description = "The overall percentage score of all the courses.")
    private Double score;

    @Schema(description = "A list of completed topics")
    private Set<QuestionDto> completedQuestions = new HashSet<>();

    @Schema(description = "A list of all chosen tests")
    private TestDto test;

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
