package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
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
@DecoratedWith(CourseEnrollmentMapperDecorator.class)
public interface CourseEnrollmentMapper {

    CourseEnrollmentMapper INSTANCE = Mappers.getMapper(CourseEnrollmentMapper.class);

    /**
     * This method maps a course enrollment entity to its equivalent course enrollment dto
     *
     * @param courseEnrollment - the course enrollment entity to be mapped
     * @return the mapped course enrollment dto
     */
    @Mappings(value = {
            @Mapping(target = "course", ignore = true),
            @Mapping(target = "student", ignore = true),
            @Mapping(target = "testEnrollments", ignore = true),
    })
    CourseEnrollmentDto toDto(CourseEnrollment courseEnrollment);

    @Mappings(value = {
            @Mapping(target = "course", ignore = true),
            @Mapping(target = "student", ignore = true),
            @Mapping(target = "testEnrollments", ignore = true),
    })
    CourseEnrollment toEntity(CourseEnrollmentDto courseEnrollmentDto);
}
