package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import ke.or.explorersanddevelopers.lms.enums.ReviewTypeEnum;
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
@Schema(name = "Review Dto", description = "Review Dto details")
public class ReviewDto implements Serializable {

    private static final long serialVersionUID = -1748895136346856830L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "A review record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal reviewId;

    @NotNull
    @Schema(example = "COURSE", description = "The type of review.")
    private ReviewTypeEnum type;

    @NotNull
    @Schema(example = "1", description = "The provided rating.")
    private Integer rating;

    @Schema(example = "A good course for beginner.", description = "The review content.")
    private String content;

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
