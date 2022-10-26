package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Thursday 13/10/2022
 **/
public interface TopicService {

    /**
     * This is a method to add a topic in a given course
     *
     * @param courseId - required to map a topic to a given course
     * @param topicDto - the topic details required to add a topic
     * @return - the added topic
     */
    TopicDto createNewTopic(BigDecimal courseId, TopicDto topicDto);

    /**
     *  This is a method to retrieve a topic by its id
     * @param topicId - required to retrieve the topic
     * @return - returns details of the retrieved topic
     */
    TopicDto getTopicById(BigDecimal topicId);

    /**
     * This is a method to delete  a given topic
     *
     * @param topicId - required to delete a specific topic
     * @return - true if a topic is successfully deleted
     */
    Boolean  deleteTopicById(BigDecimal topicId);

    /**
     * THis is a method to retrieve a list of topics from the database
     *
     * @param pageable - a page object of course with useful information such as total pages
     * @return - a list of topics from the database
     */
    List<TopicDto> getListOfTopics(Pageable pageable);

    /**
     * This is a method to edit a given topic
     *
     * @param topicId - required to retrieve and edit the topic
     * @param topicDto - topic details required to edit  topic
     * @return - the edited topic
     */
    TopicDto editTopicById(BigDecimal topicId, TopicDto topicDto);

    /**
     * This is a method to delete all topics in  given course
     *
     * @return true if all topics are deleted
     */
    Boolean deleteAllTopics();


}
