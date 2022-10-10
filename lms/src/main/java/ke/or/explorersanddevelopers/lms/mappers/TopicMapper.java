package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
@Mapper(componentModel = "spring")
@DecoratedWith(TopicMapperDecorator.class)
public interface TopicMapper {
    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    /**
     * This method maps a topic entity to its equivalent topic dto
     *
     * @param topic - the entity to be mapped
     * @return the mapped topic dto
     */
    @Mappings(value = {
            @Mapping(target = "subTopics", ignore = true)
    })
    TopicDto toDto(Topic topic);
}
