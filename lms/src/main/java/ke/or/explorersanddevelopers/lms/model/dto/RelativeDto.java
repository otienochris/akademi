package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Relative Dto", description = "Relative Dto details")
public class RelativeDto extends RepresentationModel<RelativeDto> implements Serializable {

    private static final long serialVersionUID = -9060854159005391677L;

    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Relative record id")
    private BigDecimal relativeId;

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

    @NotNull
    @Schema(example = "PARENT", description = "How the relative relates to the student.")
    private RelativeTypeEnum relativeType;

    @NotNull
    @Schema(example = "ACTIVE", description = "What role does the relative play?")
    private RelativeRoleEnum role;

    @Schema(example = "true", description = "Is account disabled?")
    private boolean isAccountDisabled;

    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Email verification code.")
    private UUID emailVerificationCode;

    @Null
    @Schema(description = "A list of users' addresses", accessMode = Schema.AccessMode.READ_ONLY)
    private List<AddressDto> addresses;

    @Null
    @Schema(description = "A list of reviews")
    private List<ReviewDto> reviews;

    @Null
    @Schema(description = "A list of students the relative is tracking")
    private List<StudentDto> students;

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
