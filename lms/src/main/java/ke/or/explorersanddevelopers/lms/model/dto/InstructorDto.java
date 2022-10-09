package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
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
@ApiModel(value = "Instructor Dto", description = "Instructor Dto details")
public class InstructorDto implements Serializable {

    private static final long serialVersionUID = -3809163225840373366L;
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Instructor record id.")
    private BigDecimal instructorId;

    @NotNull
    @ApiModelProperty(example = "Christopher", notes = "User's first name.")
    private String firstName;

    @NotNull
    @ApiModelProperty(example = "Otieno", notes = "User's last name.")
    private String lastName;

    @NotNull
    @Email
    @ApiModelProperty(example = "xyz@mno.com", notes = "User's email.")
    private String email;

    @ApiModelProperty(example = "KE", notes = "User's country of origin.")
    private String countryCode;

    @ApiModelProperty(example = "Software Engineering", notes = "Instructor's ares of expertise.")
    private String expertise;

    @ApiModelProperty(example = "A Software Engineer", notes = "An instructor's title.")
    private String title;

    @ApiModelProperty(example = "An experienced software engineer.", notes = "A user's verbose description.")
    private String description;

    @ApiModelProperty(example = "true", notes = "Is account diabled?")
    private boolean isAccountDisabled;

    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Email verification code.")
    private UUID emailVerificationCode;

    @ApiModelProperty(notes = "A list of users' addresses")
    private List<Address> addresses;

    @ApiModelProperty(notes = "A list of reviews")
    private List<Review> reviews;

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
