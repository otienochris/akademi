package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
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
@Schema(name = "Topic Dto", description = "Topic Dto details")
public class TopicDto extends RepresentationModel<TopicDto> implements Serializable {

    private static final long serialVersionUID = 6149467891720051118L;
    @Null
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Topic record id.", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal topicId;

    @NotNull
    @Schema(example = "The first topic.", description = "A topic's title.")
    private String title;

    @NotNull
    @Schema(example = "A sample topic description.", description = "A topic's description.")
    private String description;

    @Schema(example = "https://xyz.com/video/sds", description = "A link for the the topic's video.")
    private String link;

    @Schema(example = "<p> Some random content for the topic ... </p>", description = "A transcribed content of the topic.")
    private String content;

    @Schema(description = "A list of subtopics belonging to this particular topic.")
    private List<SubTopicDto> subTopics;

    @Schema(description = "Course associated  with the topic", accessMode = Schema.AccessMode.READ_ONLY)
    private Course course;

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
