package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

//@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "StudentDto", description = "Student Dto details")
public class StudentDto extends RepresentationModel<StudentDto> implements Serializable {

    private static final long serialVersionUID = 2266645598292072269L;

    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Student record id.")
    private BigDecimal studentId;

    @Null
    @Schema(description = "Students' certificates", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<CertificateDto> certificates = new HashSet<>();

    @NotNull
    @Schema(example = "Christopher", description = "User's first name.")
    private String firstName;

    @NotNull
    @Schema(example = "Otieno", description = "User's last name.")
    private String lastName;

    @NotNull
    @Email
    @Schema(example = "xyz@mno.com", description = "User's email.")
    private String email;

    @Schema(example = "KE", description = "User's country of origin.")
    private String countryCode;

    @Schema(example = "true", description = "Is account disabled?")
    private boolean isAccountDisabled;

    @Null
    @Schema(description = "A list of users' addresses", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<AddressDto> addresses = new HashSet<>();

    @Null
    @Schema(description = "A list of reviews", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<ReviewDto> reviews = new HashSet<>();

    @Null
    @Schema(description = "A list of relatives tracking the student", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<RelativeDto> relatives = new HashSet<>();

    @Null
    @Schema(description = "A list of organizations", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<OrganizationDto> organizations = new HashSet<>();

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

    @Schema(description = "User password", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String newPassword;
}
