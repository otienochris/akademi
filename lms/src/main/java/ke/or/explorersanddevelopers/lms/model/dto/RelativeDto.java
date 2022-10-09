package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.enums.RelativeRoleEnum;
import ke.or.explorersanddevelopers.lms.enums.RelativeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

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
@ApiModel(value = "Relative Dto", description = "Relative Dto details")
public class RelativeDto extends RepresentationModel<RelativeDto> implements Serializable {

    private static final long serialVersionUID = -9060854159005391677L;

    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Relative record id")
    private BigDecimal relativeId;

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

    @NotNull
    @ApiModelProperty(example = "PARENT", notes = "How the relative relates to the student.")
    private RelativeTypeEnum relativeType;

    @NotNull
    @ApiModelProperty(example = "ACTIVE", notes = "What role does the relative play?")
    private RelativeRoleEnum role;

    @ApiModelProperty(example = "true", notes = "Is account disabled?")
    private boolean isAccountDisabled;

    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Email verification code.")
    private UUID emailVerificationCode;

    @Null
    @ApiModelProperty(notes = "A list of users' addresses", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private List<AddressDto> addresses;

    @Null
    @ApiModelProperty(notes = "A list of reviews")
    private List<ReviewDto> reviews;

    @Null
    @ApiModelProperty(notes = "A list of students the relative is tracking")
    private List<StudentDto> students;

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
