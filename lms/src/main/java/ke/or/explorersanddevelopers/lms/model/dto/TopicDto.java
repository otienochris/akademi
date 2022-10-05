package ke.or.explorersanddevelopers.lms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ke.or.explorersanddevelopers.lms.model.entity.SubTopic;
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
@ApiModel(value = "Topic Dto", description = "Topic Dto details")
public class TopicDto implements Serializable {

    private static final long serialVersionUID = 6149467891720051118L;
    @Null
    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426614174000", notes = "Topic record id.", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private BigDecimal topicId;

    @NotNull
    @ApiModelProperty(example = "The first topic.", notes = "A topic's title.")
    private String title;

    @NotNull
    @ApiModelProperty(example = "A sample topic description.", notes = "A topic's description.")
    private String description;

    @ApiModelProperty(example = "https://xyz.com/video/sds", notes = "A link for the the topic's video.")
    private String link;

    @ApiModelProperty(example = "<p> Some random content for the topic ... </p>", notes = "A transcribed content of the topic.")
    private String content;

    @ApiModelProperty(notes = "A list of subtopics belonging to this particular topic.")
    private List<SubTopic> subTopics = new ArrayList<>();

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
