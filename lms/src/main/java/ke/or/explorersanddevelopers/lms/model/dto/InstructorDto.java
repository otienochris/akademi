package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Instructor Dto", description = "Instructor Dto details")
public class InstructorDto extends RepresentationModel<InstructorDto> implements Serializable {

    private static final long serialVersionUID = -3809163225840373366L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Instructor record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal instructorId;

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

    @Schema(example = "Software Engineering", description = "Instructor's ares of expertise.")
    private String expertise;

    @Schema(example = "A Software Engineer", description = "An instructor's title.")
    private String title;

    @Schema(example = "An experienced software engineer.", description = "A user's verbose description.")
    private String description;

    @Schema(example = "true", description = "Is account diabled?")
    private boolean isAccountDisabled;

    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Email verification code.")
    private UUID emailVerificationCode;

    @Null
    @Schema(description = "A list of users' addresses", accessMode = Schema.AccessMode.READ_ONLY)
    private List<AddressDto> addresses;

    @Null
    @Schema(description = "A list of reviews", accessMode = Schema.AccessMode.READ_ONLY)
    private List<ReviewDto> reviews;

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
