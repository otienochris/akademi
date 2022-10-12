package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.SubTopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.SubTopic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */

@Mapper(componentModel = "spring")
public interface SubTopicMapper {

    SubTopicMapper INSTANCE = Mappers.getMapper(SubTopicMapper.class);

    /**
     * This method maps a subtopic entity to its equivalent subtopic dto
     *
     * @param subTopic - the entity to be mapped
     * @return the mapped subtopic
     */
    SubTopicDto toDto(SubTopic subTopic);

    /**
     * This method maps a subtopic dto to its equivalent subtopic entity
     *
     * @param subTopicDto - the dto to be mapped
     * @return the mapped subtopic entity
     */
    SubTopic toEntity(SubTopicDto subTopicDto);
}
