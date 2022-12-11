package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.mappers.decorators.InstructorMapperDecorator;
import ke.or.explorersanddevelopers.lms.model.dto.InstructorDto;
import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Monday, 10/10/2022
 */
@Mapper(componentModel = "spring")
@DecoratedWith(InstructorMapperDecorator.class)
public interface InstructorMapper {
    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    /**
     * This method maps an instructor dto to its equivalent instructor entity
     *
     * @param instructorDto - the dto to be mapped
     * @return the mapped instructor entity
     */
    @Mappings(value = {
            @Mapping(target = "addresses", ignore = true),
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "courses", ignore = true),
    })
    Instructor toEntity(InstructorDto instructorDto);

    /**
     * This method maps an instructor entity to its equivalent instructor dto
     *
     * @param savedInstructor - the instructor entity to be mapped
     * @return the mapped instructor dto
     */
    @Mappings(value = {
            @Mapping(target = "addresses", ignore = true),
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "courses", ignore = true),
    })
    InstructorDto toDto(Instructor savedInstructor);
}
