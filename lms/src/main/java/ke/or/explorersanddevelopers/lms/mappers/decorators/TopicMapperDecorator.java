package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.SubTopicMapper;
import ke.or.explorersanddevelopers.lms.mappers.TopicMapper;
import ke.or.explorersanddevelopers.lms.model.dto.SubTopicDto;
import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.SubTopic;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */

public class TopicMapperDecorator implements TopicMapper {

    @Autowired
    @Qualifier("delegate")
    private TopicMapper topicMapper;

    @Autowired
    private SubTopicMapper subTopicMapper;

    @Override
    public TopicDto toDto(Topic topic) {
        TopicDto mappedTopicDto = topicMapper.toDto(topic);

        mappedTopicDto.setSubTopics(new HashSet<>());
        Set<SubTopic> subTopics = topic.getSubTopics();
        if (subTopics != null && !subTopics.isEmpty()) {
            subTopics.forEach(subTopic -> mappedTopicDto.getSubTopics().add(subTopicMapper.toDto(subTopic)));
        } else {
            mappedTopicDto.setSubTopics(new HashSet<>());
        }
        return mappedTopicDto;
    }

    @Override
    public Topic toEntity(TopicDto topicDto) {
        Topic mappedTopic = topicMapper.toEntity(topicDto);

        mappedTopic.setSubTopics(new HashSet<>());
        Set<SubTopicDto> subTopicDtoList = topicDto.getSubTopics();
        if (subTopicDtoList != null && !subTopicDtoList.isEmpty()) {
            subTopicDtoList.forEach(subTopic -> mappedTopic.getSubTopics().add(subTopicMapper.toEntity(subTopic)));
        } else {
            mappedTopic.setSubTopics(new HashSet<>());
        }
        return mappedTopic;
    }
}
