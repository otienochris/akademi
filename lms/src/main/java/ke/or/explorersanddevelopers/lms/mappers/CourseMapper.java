package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.Course;
import org.mapstruct.Mapper;

/**
 * @author: oduorfrancis134@gmail.com;
 * created_on: Wednesday 28/09/2022
 **/

@Mapper(componentModel = "spring")
public interface CourseMapper {
    //convert Entity to Dto
    CourseDto toDto(Course course);

    //convert Dto to Entity
//    Course toEntity(CourseDto courseDto);
}
