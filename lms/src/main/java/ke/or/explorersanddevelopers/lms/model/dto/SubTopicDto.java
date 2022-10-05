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
@ApiModel(value = "Subtopic Dto", description = "Subtopic Dto details")
public class SubTopicDto implements Serializable {

    private static final long serialVersionUID = -6899713947264547824L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Subtopic record id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private BigDecimal subTopicId;

    @NotNull
    @ApiModelProperty(example = "The first subtopic.", notes = "Subtopic title.")
    private String title;

    @NotNull
    @ApiModelProperty(example = "The introducing subtopic.", notes = "Subtopic description")
    private String description;

    @ApiModelProperty(example = "https://sfsfs/fsf", notes = "The subtopic video link.")
    private String link;

    @ApiModelProperty(example = "<p>some useful content ... </p>", notes = "Subtopic content.")
    private String content;

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
