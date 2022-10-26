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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Thursday 13/10/2022
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class TopicServiceImp implements TopicService {
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
        Course associatedCourse = topicEntity.getCourse();
        if (associatedCourse == null)
            topicEntity.setCourse(new Course());
        topicEntity.getCourse().setCourseId(courseId);

        Topic createdTopic = topicRepository.save(topicEntity);

        // Associate a course to the created topic
        List<Topic> topics = course.getTopics();
        if (topics == null)
            course.setTopics(new ArrayList<>());
        course.getTopics().add(createdTopic);
        courseRepository.save(course);

        log.info("Successfully created a topic");
        return topicMapper.toDto(createdTopic);
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
                .orElseThrow(() -> new NoSuchRecordException("Topic with id: " + topicId + " not found"));

        log.info("Successfully retrieved the topic with id: " + topicId);
        return topicMapper.toDto(topic);
    }


    @Override
    public Boolean deleteTopicById(BigDecimal topicId) {
        log.info("Deleting the topic with id: " + topicId);

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NoSuchRecordException("Topic with id: " + topicId + " not found"));

        topicRepository.delete(topic);

        log.info("Topic successfully deleted");
        return true;
    }

    @Override
    public List<TopicDto> getListOfTopics(Pageable pageable) {
        log.info("Retrieving all the topics");

        List<TopicDto> listOfTopics = new ArrayList<>();
        topicRepository.findAll((Sort) pageable).forEach(topic -> listOfTopics.add(topicMapper.toDto(topic)));

        if (listOfTopics.size() == 0)
            log.warn("Retrieved an empty list of topics");
        else
            log.info("Successfully retrieved a list of topics");

        return listOfTopics;
    }

    @Override
    public TopicDto editTopicById(BigDecimal topicId, TopicDto topicDto) {
        log.info("Editing a topic with the following id: " + topicId);

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NoSuchRecordException("Topic with id: " + topicId + " not found"));

        topic.setCourse(topicDto.getCourse());
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
