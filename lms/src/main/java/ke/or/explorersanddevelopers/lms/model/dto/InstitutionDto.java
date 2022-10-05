package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
import java.util.ArrayList;
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
@ApiModel(value = "Institution Dto", description = "Institution Dto details")
public class InstitutionDto implements Serializable {

    private static final long serialVersionUID = -5547803570477057596L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "An institution record id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private BigDecimal institutionId;

    @NotNull
    @ApiModelProperty(example = "Egerton University", notes = "A title for the institution.")
    private String title;

    @ApiModelProperty(example = "Transforming lives through quality education.", notes = "Institution's motto or description.")
    private String description;

    @NotNull
    @ApiModelProperty(example = "UNIVERSITY", notes = "Institution type.")
    private InstitutionTypeEnum type;

    @ApiModelProperty(notes = "Institution's address.")
    private Address address;

    @ApiModelProperty(notes = "Institution's logo in bytes.")
    private byte[] logo;

    @ApiModelProperty(notes = "A list of reviews.")
    private List<Review> reviews = new ArrayList<>();

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
