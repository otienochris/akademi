package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.TopicMapper;
import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import ke.or.explorersanddevelopers.lms.repositories.CourseRepository;
import ke.or.explorersanddevelopers.lms.repositories.TopicRepository;
import ke.or.explorersanddevelopers.lms.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Thursday 13/10/2022
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class TopicServiceImp implements TopicService {
    public static final String TOPIC_WITH_ID = "Topic with id: ";
    public static final String NOT_FOUND = " not found";
    private final CourseRepository courseRepository;
    private final TopicMapper topicMapper;

    private final TopicRepository topicRepository;

    @Override
    public TopicDto createNewTopic(BigDecimal courseId, TopicDto topicDto) {
        log.info("Creating a new topic");

        //check if the course to be mapped to the topic exists
        Course course = getCourseByIdFromDb(courseId);

        //Associate a topic to a given course
        Topic topicEntity = topicMapper.toEntity(topicDto);

        Topic savedTopic = topicRepository.save(topicEntity);

        // Associate a course to the created topic
        Set<Topic> topics = course.getTopics();
        if (topics == null)
            course.setTopics(new HashSet<>());
        course.getTopics().add(savedTopic);
        courseRepository.save(course);

        log.info("Successfully created a topic");
        return topicMapper.toDto(savedTopic);
    }

    //method to retrieve the course associated with a given topic
    private Course getCourseByIdFromDb(BigDecimal courseId) {
        return courseRepository.getByCourseId(courseId).orElseThrow(() -> {
                    String message = "Cannot find the associated course to add the topic: " + "the course id you " +
                            "provided: " + courseId + ".";
                    log.error(message);
                    throw new NoSuchRecordException(message);
                    }
        );
    }

    @Override
    public TopicDto getTopicById(BigDecimal topicId) {
        log.info("Retrieving topic with the following id :" + topicId);

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NoSuchRecordException(TOPIC_WITH_ID + topicId + NOT_FOUND));

        log.info("Successfully retrieved the topic with id: " + topicId);
        return topicMapper.toDto(topic);
    }


    @Override
    public Boolean deleteTopicById(BigDecimal topicId) {
        log.info("Deleting the topic with id: " + topicId);

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NoSuchRecordException(TOPIC_WITH_ID + topicId + NOT_FOUND));

        topicRepository.delete(topic);

        log.info("Topic successfully deleted");
        return true;
    }

    @Override
    public List<TopicDto> getListOfTopics(Pageable pageable) {
        log.info("Retrieving all the topics");

        List<TopicDto> listOfTopics = new ArrayList<>();
        topicRepository.findAll((Sort) pageable).forEach(topic -> listOfTopics.add(topicMapper.toDto(topic)));

        if (listOfTopics.isEmpty())
            log.warn("Retrieved an empty list of topics");
        else
            log.info("Successfully retrieved a list of topics");

        return listOfTopics;
    }

    @Override
    public TopicDto editTopicById(BigDecimal topicId, TopicDto topicDto) {
        log.info("Editing a topic with the following id: " + topicId);

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NoSuchRecordException(TOPIC_WITH_ID + topicId + NOT_FOUND));

//        topic.setCourse(topicDto.getCourse());
        topic.setDescription(topicDto.getDescription());
        topic.setVersion(topicDto.getVersion());
        topic.setTitle(topicDto.getTitle());
        topic.setContent(topicDto.getContent());
        topic.setLink(topicDto.getLink());

        //save the updated topic
        final Topic updatedTopic = topicRepository.save(topic);

        return topicMapper.toDto(updatedTopic);
    }

    @Override
    public Boolean deleteAllTopics() {
        log.info("Deleting all topics");
        topicRepository.deleteAll();
        log.info("Successfully deleted all topics");

        return true;
    }
}
