package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.mappers.decorators.RelativeMapperDecorator;
import ke.or.explorersanddevelopers.lms.model.dto.RelativeDto;
import ke.or.explorersanddevelopers.lms.model.entity.Relative;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 09/10/2022
 */
@Mapper(componentModel = "spring")
@DecoratedWith(RelativeMapperDecorator.class)
public interface RelativeMapper {

    RelativeMapper INSTANCE = Mappers.getMapper(RelativeMapper.class);

    /**
     * This maps a relative entity to its equivalent relative dto
     *
     * @param relative - the entity to be mapped
     * @return the mapped dto
     */
    @Mappings(value = {
            @Mapping(target = "addresses", ignore = true),
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "students", ignore = true)
    })
    RelativeDto toDto(Relative relative);

    @Mappings(value = {
            @Mapping(target = "addresses", ignore = true),
            @Mapping(target = "reviews", ignore = true),
    })
    Relative toEntity(RelativeDto relativeDto);
}
