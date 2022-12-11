package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.mappers.decorators.QuestionMapperDecorator;
import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
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
@DecoratedWith(QuestionMapperDecorator.class)
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    /**
     * This method maps a question entity to its equivalent qustion dto
     *
     * @param question - the entity to be mapped
     * @return the mapped dto
     */
    @Mappings(value = {
            @Mapping(target = "answers", ignore = true)
    })
    QuestionDto toDto(Question question);

    /**
     * This method maps a question dto to its equivalent question entity
     *
     * @param questionDto - the dto to be mapped
     * @return the mapped entity
     */
    @Mappings(value = {
            @Mapping(target = "answers", ignore = true)
    })
    Question toEntity(QuestionDto questionDto);
}
