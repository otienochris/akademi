package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Certificate Dto", description = "Certificate Dto details")
public class CertificateDto implements Serializable {

    private static final long serialVersionUID = -6684621063380419878L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Certificate Record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal certificateId;

    @NotNull
    @Schema(example = "COMPLETION", description = "Certificate type.")
    private CertificateTypeEnum type;

    @Schema(example = "Introduction to something new.", description = "The title of the course a student completed to earn this certificate.")
    private String courseTitle;

    @NotNull
    @Schema(example = "GENERATED", description = "Has the user already generated or downloaded the certificate?")
    private CertificateStatusEnum status;

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
