package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.SubTopicDto;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Wednesday 26/10/2022
 **/
public interface SubTopicService {

    /**
     *  This method creates  new subtopic and associates it to a given Topic
     *
     * @param topicId - the topic id to be associated with the subtopic
     * @param subTopicDto - details of the subtopic to be created
     * @return the created subtopic
     */
    SubTopicDto createNewSubTopic(BigDecimal topicId, SubTopicDto subTopicDto);

    /**
     * This method retrieves a subtopic
     *
     * @param subTopicId the id of the subtopic to be retrieved
     * @return  the details of the subtopic to be retrieved
     */
    SubTopicDto getSubTopicById(BigDecimal subTopicId);

    /**
     * This method deletes a subtopic by id
     *
     * @param subTopicId the id of the subtopic to be deleted
     * @return true is a subtopic is deleted
     */
    Boolean deleteSubtopicById(BigDecimal subTopicId);

    /**
     *  THis method retrieves a list of sub topics from the database
     *
     * @param pageable a page object of subtopic with useful information such as total pages
     * @return a list of sub topics
     */
    List<SubTopicDto> getListOfSubTopics(Pageable pageable);

    /**
     *This method retrieves and edits a subtopic of a given id
     *
     * @param subTopicId - the id of the subtopic to be retrieved
     * @param subTopicDto - the new details of the subtopic to be updates
     * @return the updated subtopic
     */

    SubTopicDto editSubTopicById(BigDecimal subTopicId, SubTopicDto subTopicDto);


    /**
     * This method deletes all subtopics in the database
     *
     * @return true if all sub topics are successfully deleted
     */
    Boolean deleteAllSubtopics();

}
