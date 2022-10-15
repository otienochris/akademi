package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import ke.or.explorersanddevelopers.lms.model.entity.Test;
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
@DecoratedWith(TestMapperDecorator.class)
public interface TestMapper {

    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);


    /**
     * This method maps a test entity to its equivalent test dto
     *
     * @param test - the test entity to be mapped
     * @return the mapped test dto
     */
    @Mappings(value = {
            @Mapping(target = "questions", ignore = true)
    })
    TestDto toDto(Test test);

    /**
     * This method maps a test dto to its equivalent test entity
     *
     * @param testDto - the test dto to be mapped
     * @return the mapped test entity
     */
    @Mappings(value = {
            @Mapping(target = "questions", ignore = true)
    })
    Test toEntity(TestDto testDto);
}
