package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import org.mapstruct.*;

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
            @Mapping(target = "topics", ignore = true)
    })
        //convert Entity to Dto
    CourseDto toDto(Course course);
    //convert Dto to Entity
    @Mappings(value = {
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "courseEnrollments", ignore = true),
            @Mapping(target = "topics", ignore = true)
    })
    Course toEntity(CourseDto courseDto);
}
