package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.SubTopicMapper;
import ke.or.explorersanddevelopers.lms.model.dto.SubTopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.SubTopic;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import ke.or.explorersanddevelopers.lms.repositories.SubTopicRepository;
import ke.or.explorersanddevelopers.lms.repositories.TopicRepository;
import ke.or.explorersanddevelopers.lms.service.SubTopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Wednesday 26/10/2022
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class SubTopicServiceImpl implements SubTopicService {

    private final SubTopicRepository subTopicRepository;

    private final TopicRepository topicRepository;
    private final SubTopicMapper subTopicMapper;

    @Override
    public SubTopicDto createNewSubTopic(BigDecimal topicId, SubTopicDto subTopicDto) {
        log.info("Creating a  subtopic");

        //check if the topic to be mapped to the subtopic exist
        Topic topic = getTopicById(topicId);

        //bind the subtopic to the topic
        SubTopic subTopicEntity = subTopicMapper.toEntity(subTopicDto);

        SubTopic createdSubTopic = subTopicRepository.save(subTopicEntity);

        //bind the topic to the subtopic
        Set<SubTopic> subTopicList = topic.getSubTopics();
        if (subTopicList == null)
            topic.setSubTopics(new HashSet<>());
        topic.getSubTopics().add(createdSubTopic);
        topicRepository.save(topic);

        log.info("Successfully created a sub topic");


        return subTopicMapper.toDto(createdSubTopic);
    }

    // Retrieve topic to be mapped to the subtopic
    private Topic getTopicById(BigDecimal topicId) {
        return topicRepository.getByTopicId(topicId)
                .orElseThrow(() -> new NoSuchRecordException("Topic with id: " + topicId + " does not exist"));

    }

    @Override
    public SubTopicDto getSubTopicById(BigDecimal subTopicId) {
        log.info("Retrieving a sub topic with id: " + subTopicId);

        SubTopic subTopic = subTopicRepository.getBySubTopicId(subTopicId)
                .orElseThrow(() -> new NoSuchRecordException("Sub topic with id: " + subTopicId + "does not exist"));

        log.info("Successfully retrieved sub topic with id: " + subTopicId);

        return subTopicMapper.toDto(subTopic);
    }

    @Override
    public Boolean deleteSubtopicById(BigDecimal subTopicId) {
        log.info("Deleting a subtopic with id: " + subTopicId);

        SubTopic subTopic = subTopicRepository.findById(subTopicId)
                .orElseThrow(() -> new NoSuchRecordException("Sub topic with id: " + subTopicId + " does not " +
                        "exist"));

        subTopicRepository.delete(subTopic);
        log.info("Successfully deleted the subtopic");

        return true;
    }

    @Override
    public List<SubTopicDto> getListOfSubTopics(Pageable pageable) {
        log.info("Retrieving a list of sub topics");

        List<SubTopicDto> subTopicDtoList = new ArrayList<>();

        subTopicRepository.findAll(pageable).forEach(subTopic -> subTopicDtoList.add(subTopicMapper.toDto(subTopic)));

        if (subTopicDtoList.isEmpty())
            log.warn("Retrieved n empty list");
        else
            log.info("Successfully retrieved a list of sub topics");

        return subTopicDtoList;
    }

    @Override
    public SubTopicDto editSubTopicById(BigDecimal subTopicId, SubTopicDto subTopicDto) {
        log.info("Retrieving sub topic with id :" + subTopicId + " for editing");

        SubTopic subTopic = subTopicRepository.findById(subTopicId)
                .orElseThrow(() -> new NoSuchRecordException("Sub topic with id " + subTopicId + "not found"));

        log.info("Subtopic found, proceeding to updating");

        subTopic.setContent(subTopicDto.getContent());
        subTopic.setTitle(subTopicDto.getTitle());
        subTopic.setDescription(subTopicDto.getDescription());
        subTopic.setLink(subTopicDto.getLink());
        subTopic.setVersion(subTopicDto.getVersion());

        final SubTopic updatedSubTopic = subTopicRepository.save(subTopic);

        return subTopicMapper.toDto(updatedSubTopic);
    }

    @Override
    public Boolean deleteAllSubtopics() {
        log.warn("Deleting all sub topics");
        subTopicRepository.deleteAll();
        log.info("All sub topics deleted");

        return true;
    }
}
