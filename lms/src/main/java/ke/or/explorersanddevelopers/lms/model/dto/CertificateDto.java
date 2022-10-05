package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.enums.CertificateStatusEnum;
import ke.or.explorersanddevelopers.lms.enums.CertificateTypeEnum;
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
@ApiModel(value = "Certificate Dto", description = "Certificate Dto details")
public class CertificateDto implements Serializable {

    private static final long serialVersionUID = -6684621063380419878L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Certificate Record id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private BigDecimal certificateId;

    @NotNull
    @ApiModelProperty(example = "COMPLETION", notes = "Certificate type.")
    private CertificateTypeEnum type;

    @ApiModelProperty(example = "Introduction to something new.", notes = "The title of the course a student completed to earn this certificate.")
    private String courseTitle;

    @NotNull
    @ApiModelProperty(example = "GENERATED", notes = "Has the user already generated or downloaded the certificate?")
    private CertificateStatusEnum status;

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
