package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
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

//@Data
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Student Dto", description = "Student Dto details")
public class StudentDto extends RepresentationModel<StudentDto> implements Serializable {

    private static final long serialVersionUID = 2266645598292072269L;

    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Student record id.")
    private BigDecimal studentId;

    @Null
    @ApiModelProperty(notes = "Students' certificates", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private List<CertificateDto> certificates = new ArrayList<>();

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

    @ApiModelProperty(example = "true", notes = "Is account disabled?")
    private boolean isAccountDisabled;

    @Null
    @ApiModelProperty(notes = "A list of users' addresses", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private List<AddressDto> addresses = new ArrayList<>();

    @Null
    @ApiModelProperty(notes = "A list of reviews", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private List<ReviewDto> reviews = new ArrayList<>();

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
