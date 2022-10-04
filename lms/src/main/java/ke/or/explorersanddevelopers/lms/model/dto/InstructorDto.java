package ke.or.explorersanddevelopers.lms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
@ApiModel(value = "Instructor Dto", description = "Instructor Dto details")
public class InstructorDto extends UserDto implements Serializable {

    private static final long serialVersionUID = -3809163225840373366L;
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Instructor record id.")
    private UUID instructorId;

    @ApiModelProperty(example = "Software Engineering", notes = "Instructor's ares of expertise.")
    private String expertise;

    @ApiModelProperty(example = "A Software Engineer", notes = "An instructor's title.")
    private String title;

    @ApiModelProperty(example = "An experienced software engineer.", notes = "A user's verbose description.")
    private String description;
}
