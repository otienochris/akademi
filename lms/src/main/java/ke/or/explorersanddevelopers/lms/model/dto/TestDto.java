package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
import java.util.ArrayList;
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
@ApiModel(value = "Test Dto", description = "Test Dto details")
public class TestDto implements Serializable {

    private static final long serialVersionUID = -878009969108979322L;
    @Null
    @ApiModelProperty(example = "", notes = "", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private BigDecimal testId;

    @ApiModelProperty(notes = "topics")
    private List<TopicDto> topics = new ArrayList<>();

    @NotNull
    @ApiModelProperty(example = "EXAM", notes = "The type of test.")
    private TestTypeEnum testType;

    @NotNull
    @ApiModelProperty(example = "N", notes = "Is the test optional.")
    private YesOrNoEnum isOptional;

    @NotNull
    @ApiModelProperty(example = "Y", notes = "Is the test scheduled.")
    private YesOrNoEnum isScheduled;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2022-01-01", notes = "The date the test starts")
    private Date startDateAndTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2022-02-01", notes = "The date the test ends.")
    private Date endDateAndTime;

    @ApiModelProperty(notes = "Questions within the test.")
    private List<QuestionDto> questions = new ArrayList<>();

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
