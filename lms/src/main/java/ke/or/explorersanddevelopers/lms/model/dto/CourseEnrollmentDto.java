package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
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
@Schema(name = "Course Enrollment Dto", description = "Course Enrollment Dto details")
public class CourseEnrollmentDto implements Serializable {

    private static final long serialVersionUID = -926809233618989828L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Course Enrollment record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal courseEnrollmentId;

    @NotNull
    @Schema(example = "COMPLETE", description = "Course Enrollment status.")
    private StatusEnum status;

    @Schema(example = "100", description = "Amount the student paid when enrolling for the case.")
    private BigDecimal amount;

    @Schema(example = "2022-02-01", description = "The date the student completed the course.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completionDate;

    @Schema(description = "The student enrolled for the course.")
    private StudentDto student;

    @Schema(description = "The course the student enrolled in.")
    private CourseDto course;

    @Schema(description = "A list of test enrollment details chosen by the student.")
    private Set<TestEnrollmentDto> testEnrollments = new HashSet<>();

    @Schema(description = "A list of completed topics.")
    private Set<TopicDto> completedTopics = new HashSet<>();

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
