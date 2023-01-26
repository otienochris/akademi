package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.AddressMapper;
import ke.or.explorersanddevelopers.lms.mappers.RelativeMapper;
import ke.or.explorersanddevelopers.lms.mappers.ReviewMapper;
import ke.or.explorersanddevelopers.lms.mappers.StudentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.RelativeDto;
import ke.or.explorersanddevelopers.lms.model.dto.ReviewDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import ke.or.explorersanddevelopers.lms.model.entity.Relative;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Sunday, 09/10/2022
 */
public class RelativeMapperDecorator implements RelativeMapper {

    @Autowired
    @Qualifier("delegate")
    private RelativeMapper relativeMapper;

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    @Qualifier("delegate")
    private StudentMapper studentMapper;

    @Override
    public RelativeDto toDto(Relative relative) {
        RelativeDto mappedRelativeDto = relativeMapper.toDto(relative);

        //map addresses
        List<Address> addresses = relative.getAddresses();
        if (addresses != null && !addresses.isEmpty()) {
            List<AddressDto> addressDtoList = new ArrayList<>();
            addresses.forEach(address -> addressDtoList.add(addressMapper.toDto(address)));
            mappedRelativeDto.setAddresses(addressDtoList);
        }

        // map reviews
        List<Review> reviews = relative.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            List<ReviewDto> reviewDtoList = new ArrayList<>();
            reviews.forEach(review -> reviewDtoList.add(reviewMapper.toDto(review)));
            mappedRelativeDto.setReviews(reviewDtoList);
        }

        return mappedRelativeDto;
    }

    @Override
    public Relative toEntity(RelativeDto relativeDto) {
        Relative mappedRelative = relativeMapper.toEntity(relativeDto);

        // map addresses
        List<AddressDto> addressDtoList = relativeDto.getAddresses();
        if (addressDtoList != null && !addressDtoList.isEmpty()) {
            List<Address> addresses = new ArrayList<>();
            addressDtoList.forEach(addressDto -> addresses.add(addressMapper.toEntity(addressDto)));
            mappedRelative.setAddresses(addresses);
        }

        // map review
        List<ReviewDto> reviewDtoList = relativeDto.getReviews();
        if (reviewDtoList != null && !reviewDtoList.isEmpty()) {
            List<Review> reviews = new ArrayList<>();
            reviewDtoList.forEach(reviewDto -> reviews.add(reviewMapper.toEntity(reviewDto)));
            mappedRelative.setReviews(reviews);
        }

        return mappedRelative;
    }
}
