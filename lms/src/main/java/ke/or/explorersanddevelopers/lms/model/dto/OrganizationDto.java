package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import ke.or.explorersanddevelopers.lms.enums.OrganizationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Organization details
 *
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Organization", description = "Organization Details")
public class OrganizationDto implements Serializable {

    private static final long serialVersionUID = -8065681046125763404L;

    @Null
    @Schema(example = "101", description = "Id of an organization record.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal organizationId;

    @NotNull
    @Schema(example = "Egerton University", description = "The organization's title or name.")
    private String title;

    @NotNull
    @Schema(example = "Transforming lives through quality educations.", description = "A verbose description of the institution")
    private String description;

    @NotNull
    @Schema(example = "UNIVERSITY", description = "Organization's type or category")
    private OrganizationTypeEnum type;

    @Schema(example = "KE", description = "The code for the country the organization is located, or registered.")
    private String countryCode;

    @Schema(description = "The organization's log")
    private byte[] logo;

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
