package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
@ApiModel(value = "Address Dto", description = "Address Dto details")
public class AddressDto implements Serializable {

    private static final long serialVersionUID = 2995478574495136701L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Address record id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private BigDecimal addressId;

    @ApiModelProperty(example = "RIVER ROAD", notes = "Address' street.")
    private String street;

    @NotNull
    @ApiModelProperty(example = "Nairobi", notes = "Address' city")
    private String city;

    @NotNull
    @ApiModelProperty(example = "Kenya", notes = "Address' state.")
    private String state;

    @ApiModelProperty(example = "00100", notes = "Address postal code.")
    private String postalCode;

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
