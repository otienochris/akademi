package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
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
@ApiModel(value = "Course Enrollment Dto", description = "Course Enrollment Dto details")
public class CourseEnrollmentDto implements Serializable {

    private static final long serialVersionUID = -926809233618989828L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Course Enrollment record id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private UUID courseEnrollmentId;

    @NotNull
    @ApiModelProperty(example = "COMPLETE", notes = "Course Enrollment status.")
    private StatusEnum status;

    @ApiModelProperty(example = "100", notes = "Amount the student paid when enrolling for the case.")
    private BigDecimal amount;

    @ApiModelProperty(example = "2022-02-01", notes = "The date the student completed the course.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completionDate;

    @ApiModelProperty(notes = "The student enrolled for the course.")
    private Student student;

    @ApiModelProperty(notes = "The course the student enrolled in.")
    private Course course;

    @ApiModelProperty(notes = "A list of test enrollment details chosen by the student.")
    private List<TestEnrollmentDto> testEnrollments = new ArrayList<>();

    @ApiModelProperty(notes = "A list of completed topics.")
    private List<TopicDto> completedTopics = new ArrayList<>();

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
