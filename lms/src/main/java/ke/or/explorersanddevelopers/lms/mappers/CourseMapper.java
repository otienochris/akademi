package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.mappers.decorators.CourseMapperDecorator;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@Mapper(componentModel = "spring")
@DecoratedWith(CourseMapperDecorator.class)
public interface CourseMapper {

    @Mappings(value = {
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "courseEnrollments", ignore = true),
            @Mapping(target = "topics", ignore = true),
            @Mapping(target = "instructors", ignore = true)
    })
        //convert Entity to Dto
    CourseDto toDto(Course course);
    //convert Dto to Entity
    @Mappings(value = {
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "courseEnrollments", ignore = true),
            @Mapping(target = "topics", ignore = true),
            @Mapping(target = "instructors", ignore = true)
    })
    Course toEntity(CourseDto courseDto);
}
