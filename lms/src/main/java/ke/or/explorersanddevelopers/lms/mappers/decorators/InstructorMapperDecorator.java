package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.AddressMapper;
import ke.or.explorersanddevelopers.lms.mappers.CourseMapper;
import ke.or.explorersanddevelopers.lms.mappers.InstructorMapper;
import ke.or.explorersanddevelopers.lms.mappers.ReviewMapper;
import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.dto.InstructorDto;
import ke.or.explorersanddevelopers.lms.model.dto.ReviewDto;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Monday, 10/10/2022
 */
public class InstructorMapperDecorator implements InstructorMapper {

    @Autowired
    @Qualifier("delegate")
    private InstructorMapper instructorMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    @Qualifier("delegate")
    private CourseMapper courseMapper;

    @Override
    public Instructor toEntity(InstructorDto instructorDto) {
        Instructor mappedInstructor = instructorMapper.toEntity(instructorDto);
        mappedInstructor.setReviews(new HashSet<>());
        mappedInstructor.setAddresses(new HashSet<>());
        mappedInstructor.setCourses(new HashSet<>());

        Set<ReviewDto> reviewDtoList = instructorDto.getReviews();
        if (reviewDtoList != null && !reviewDtoList.isEmpty()) {
            reviewDtoList.forEach(reviewDto -> mappedInstructor.getReviews().add(reviewMapper.toEntity(reviewDto)));
        }

        Set<AddressDto> addressDtoList = instructorDto.getAddresses();
        if (addressDtoList != null && !addressDtoList.isEmpty()) {
            addressDtoList.forEach(addressDto -> mappedInstructor.getAddresses().add(addressMapper.toEntity(addressDto)));
        }

        Set<CourseDto> courseDtoList = instructorDto.getCourses();
        if (courseDtoList != null && !courseDtoList.isEmpty())
            courseDtoList.forEach(courseDto -> mappedInstructor.getCourses().add(courseMapper.toEntity(courseDto)));

        return mappedInstructor;
    }

    @Override
    public InstructorDto toDto(Instructor instructor) {
        InstructorDto mappedInstructorDto = instructorMapper.toDto(instructor);
        mappedInstructorDto.setReviews(new HashSet<>());
        mappedInstructorDto.setAddresses(new HashSet<>());
        mappedInstructorDto.setCourses(new HashSet<>());

        Set<Review> reviews = instructor.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            reviews.forEach(review -> mappedInstructorDto.getReviews().add(reviewMapper.toDto(review)));
        }

        Set<Address> addresses = instructor.getAddresses();
        if (addresses != null && !addresses.isEmpty()) {
            addresses.forEach(address -> mappedInstructorDto.getAddresses().add(addressMapper.toDto(address)));
        }

        Set<Course> courses = instructor.getCourses();
        if (courses != null && !courses.isEmpty())
            courses.forEach(course -> mappedInstructorDto.getCourses().add(courseMapper.toDto(course)));

        return mappedInstructorDto;
    }
}
