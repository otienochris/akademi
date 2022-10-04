package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
import ke.or.explorersanddevelopers.lms.model.entity.Test;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@ApiModel(value = "Test Enrollment Dto", description = "Test Enrollment Dto details")
public class TestEnrollmentDto implements Serializable {

    private static final long serialVersionUID = -5163497779278050798L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Test Enrollment Record Id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private UUID testEnrollmentId;

    @NotNull
    @ApiModelProperty(example = "COMPLETED", notes = "Test enrollment status.")
    private StatusEnum status;

    @ApiModelProperty(example = "2022-01-02", notes = "The date all the tests were completed.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completionDate;

    @ApiModelProperty(example = "20", notes = "The amount the student paid when enrolling for the tests if any.")
    private BigDecimal amount;

    @ApiModelProperty(example = "20", notes = "The overall percentage score of all the courses.")
    private Double score;

    @ApiModelProperty(notes = "A list of completed topics")
    private List<Question> completedQuestions = new ArrayList<>();

    @ApiModelProperty(notes = "A list of all chosen tests")
    private List<Test> tests = new ArrayList<>();

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
