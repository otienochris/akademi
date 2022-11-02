package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

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
@Schema(name = "Subtopic Dto", description = "Subtopic Dto details")
public class SubTopicDto extends RepresentationModel<SubTopicDto> implements Serializable {

    private static final long serialVersionUID = -6899713947264547824L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Subtopic record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal subTopicId;

    @NotNull
    @Schema(example = "The first subtopic.", description = "Subtopic title.")
    private String title;

    @NotNull
    @Schema(example = "The introducing subtopic.", description = "Subtopic description")
    private String description;

    @Schema(example = "https://sfsfs/fsf", description = "The subtopic video link.")
    private String link;

    @Schema(example = "<p>some useful content ... </p>", description = "Subtopic content.")
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

    @Schema(description = "Topic associated  with the sub topic", accessMode = Schema.AccessMode.READ_ONLY)
    private Topic topic;
}
