package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@Mapper(componentModel = "spring")
public interface CourseMapper {
    //convert Entity to Dto
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "courseEnrollments", ignore = true)
    @Mapping(target = "topics", ignore = true)
    CourseDto toDto(Course course);

    //convert Dto to Entity
//    Course toEntity(CourseDto courseDto);
}
