package ke.or.explorersanddevelopers.lms.repositories;

import ke.or.explorersanddevelopers.lms.model.dto.SubTopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.SubTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Wednesday 26/10/2022
 **/
public interface SubTopicRepository extends JpaRepository<SubTopic, BigDecimal> {

    Optional<SubTopicDto>  getSubTopicById(BigDecimal subTopicId);
}
