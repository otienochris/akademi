package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 15/10/2022
 */
public interface TopicRepository extends JpaRepository<Topic, BigDecimal> {
    Optional<Topic> getByTopicId(BigDecimal topicId);
}

