package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.enums.CourseCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Course Dto", description = "Course Dto details")
public class CourseDto implements Serializable {

    private static final long serialVersionUID = 1519602186785582645L;
    @NotNull
    @ApiModelProperty(example = "You'll learn how to code like a pro!", notes = "A course's verbose description.")
    public String description;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Course record id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private UUID courseId;
    @NotNull
    @ApiModelProperty(example = "Introduction to programming.", notes = "A course's title.")
    private String title;

    @NotNull
    @ApiModelProperty(example = "https://xyz.com/image.png", notes = "A course's thumbnail image link.")
    private String thumbnailLink;

    @Min(value = 1, message = "Rating must be between 1 and 5 both included.")
    @Max(value = 5, message = "Rating must be between 1 and 5 both included.")
    @ApiModelProperty(example = "3", notes = "")
    private Integer rating;

    @ApiModelProperty(example = "10", notes = "Course's price.")
    private BigDecimal price;

    @NotNull
    @ApiModelProperty(example = "TECH_TOOLS", notes = "Course Category.")
    private CourseCategoryEnum category;

    @ApiModelProperty(example = "https://xyz.com/video/sdfsf", notes = "Course's introduction video link.")
    private String introductionVideoLink;

    @ApiModelProperty(notes = "Courses' reviews.")
    private List<ReviewDto> reviews = new ArrayList<>();

    @ApiModelProperty(notes = "A list of course enrollments for this course.")
    private List<CourseEnrollmentDto> courseEnrollments = new ArrayList<>();

    @ApiModelProperty(notes = "A list of the course's topics")
    private List<TopicDto> topics = new ArrayList<>();

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
