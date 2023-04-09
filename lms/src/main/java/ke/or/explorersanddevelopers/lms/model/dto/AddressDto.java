package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 04/10/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Address Dto", description = "Address Dto details")
public class AddressDto implements Serializable {

    private static final long serialVersionUID = 2995478574495136701L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Address record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal addressId;

    @Schema(example = "RIVER ROAD", description = "Address' street.")
    private String street;

    @NotNull
    @Schema(example = "Nairobi", description = "Address' city")
    private String city;

    @NotNull
    @Schema(example = "Kenya", description = "Address' state.")
    private String state;

    @Schema(example = "00100", description = "Address postal code.")
    private String postalCode;

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
