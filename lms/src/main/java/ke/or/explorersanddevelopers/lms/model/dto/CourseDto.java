package ke.or.explorersanddevelopers.lms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private UUID courseId;
    @NotNull(message = "Course title required")
    private String title;
    @NotNull(message = "Course description required")
    public String description;
    @NotNull(message = "Course description required")
    private String thumbnailLink;
    private Integer rating;
    private BigDecimal price;
    @NotNull(message = "Course category required")
    private String category;
    private List reviews;
    private Date creationDate;
    private Date modificationDate;
    private String introductionVideoLink;
    private List courseEnrollments;
    private List topics;


}
