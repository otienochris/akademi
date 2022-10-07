package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.AnswerDto;
import ke.or.explorersanddevelopers.lms.model.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    /**
     * This method maps an answer entity to its equivalent answer dto
     *
     * @param answer - the entity to be mapped
     * @return the mapped answer dto
     */
    AnswerDto toDto(Answer answer);
}
