package ke.or.explorersanddevelopers.lms.mapper;

import ke.or.explorersanddevelopers.lms.mappers.AddressMapper;
import ke.or.explorersanddevelopers.lms.mappers.ReviewMapper;
import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.InstructorDto;
import ke.or.explorersanddevelopers.lms.model.dto.ReviewDto;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import ke.or.explorersanddevelopers.lms.model.entity.Instructor;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Instructor toEntity(InstructorDto instructorDto) {
        Instructor mappedInstructor = instructorMapper.toEntity(instructorDto);
        mappedInstructor.setReviews(new ArrayList<>());
        mappedInstructor.setAddresses(new ArrayList<>());

        List<ReviewDto> reviewDtoList = instructorDto.getReviews();
        if (reviewDtoList != null && reviewDtoList.size() > 0) {
            reviewDtoList.forEach(reviewDto -> mappedInstructor.getReviews().add(reviewMapper.toEntity(reviewDto)));
        }

        List<AddressDto> addressDtoList = instructorDto.getAddresses();
        if (addressDtoList != null && addressDtoList.size() > 0) {
            addressDtoList.forEach(addressDto -> mappedInstructor.getAddresses().add(addressMapper.toEntity(addressDto)));
        }
        return mappedInstructor;
    }

    @Override
    public InstructorDto toDto(Instructor savedInstructor) {
        InstructorDto mappedInstructorDto = instructorMapper.toDto(savedInstructor);
        mappedInstructorDto.setReviews(new ArrayList<>());
        mappedInstructorDto.setAddresses(new ArrayList<>());

        List<Review> reviews = savedInstructor.getReviews();
        if (reviews != null && reviews.size() > 0) {
            reviews.forEach(review -> mappedInstructorDto.getReviews().add(reviewMapper.toDto(review)));
        }

        List<Address> addresses = savedInstructor.getAddresses();
        if (addresses != null && addresses.size() > 0) {
            addresses.forEach(address -> mappedInstructorDto.getAddresses().add(addressMapper.toDto(address)));
        }

        return mappedInstructorDto;
    }
}
