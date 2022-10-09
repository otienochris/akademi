package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import ke.or.explorersanddevelopers.lms.enums.InstitutionTypeEnum;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Institution Dto", description = "Institution Dto details")
public class InstitutionDto implements Serializable {

    private static final long serialVersionUID = -5547803570477057596L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "An institution record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal institutionId;

    @NotNull
    @Schema(example = "Egerton University", description = "A title for the institution.")
    private String title;

    @Schema(example = "Transforming lives through quality education.", description = "Institution's motto or description.")
    private String description;

    @NotNull
    @Schema(example = "UNIVERSITY", description = "Institution type.")
    private InstitutionTypeEnum type;

    @Schema(description = "Institution's address.")
    private Address address;

    @Schema(description = "Institution's logo in bytes.")
    private byte[] logo;

    @Schema(description = "A list of reviews.")
    private List<Review> reviews;

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
