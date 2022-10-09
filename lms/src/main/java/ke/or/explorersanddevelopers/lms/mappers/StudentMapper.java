package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */
@Mapper(componentModel = "spring")
@DecoratedWith(StudentMapperDecorator.class)
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    /**
     * This method maps a student entity to its equivalent student dto
     *
     * @param student - the student entity to be mapped
     * @return the mapped student dto
     */
    @Mappings(value = {
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "addresses", ignore = true),
            @Mapping(target = "certificates", ignore = true),
            @Mapping(target = "relatives", ignore = true)
    })
    StudentDto toDto(Student student);

    /**
     * This method maps a student dto to its equivalent student entity
     *
     * @param studentDto - the student dto to be mapped
     * @return the mapped student entity
     */
    @Mappings(value = {
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "addresses", ignore = true),
            @Mapping(target = "certificates", ignore = true),
            @Mapping(target = "relatives", ignore = true)
    })
    Student toEntity(StudentDto studentDto);
}
