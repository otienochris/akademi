package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import ke.or.explorersanddevelopers.lms.enums.CourseCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * @author oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Course Dto", description = "Course Dto details")
public class CourseDto extends RepresentationModel<CourseDto> implements Serializable {

    private static final long serialVersionUID = 1519602186785582645L;
    @NotNull
    @Schema(example = "You'll learn how to code like a pro!", description = "A course's verbose description.")
    public String description;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Course record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal courseId;
    @NotNull
    @Schema(example = "Introduction to programming.", description = "A course's title.")
    private String title;

    @NotNull
    @Schema(example = "https://xyz.com/image.png", description = "A course's thumbnail image link.")
    private String thumbnailLink;

    @Null
    @Min(value = 1, message = "Rating must be between 1 and 5 both included.")
    @Max(value = 5, message = "Rating must be between 1 and 5 both included.")
    @Schema(example = "3", description = "Rating value", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer rating;

    @Schema(example = "10", description = "Course's price.")
    private BigDecimal price;

    @NotNull
    @Schema(example = "TECH_TOOLS", description = "Course Category.")
    private CourseCategoryEnum category;

    @Schema(example = "https://xyz.com/video/sdfsf", description = "Course's introduction video link.")
    private String introductionVideoLink;

    @Null
    @Schema(description = "Courses' reviews.", accessMode = Schema.AccessMode.READ_ONLY)
    private List<ReviewDto> reviews;

    @Null
    @Schema(description = "A list of course enrollments for this course.", accessMode = Schema.AccessMode.READ_ONLY)
    private List<CourseEnrollmentDto> courseEnrollments;

    @Null
    @Schema(description = "A list of the course's topics", accessMode = Schema.AccessMode.READ_ONLY)
    private List<TopicDto> topics;

    @Null
    @Schema(description = "A list of instructors", accessMode = Schema.AccessMode.READ_ONLY)
    private List<InstructorDto> instructors;

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
