package ke.or.explorersanddevelopers.lms.mappers;

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
        if (addresses != null && addresses.size() > 0) {
            List<AddressDto> addressDtoList = new ArrayList<>();
            addresses.forEach(address -> addressDtoList.add(addressMapper.toDto(address)));
            mappedRelativeDto.setAddresses(addressDtoList);
        }

        // map reviews
        List<Review> reviews = relative.getReviews();
        if (reviews != null && reviews.size() > 0) {
            List<ReviewDto> reviewDtoList = new ArrayList<>();
            reviews.forEach(review -> reviewDtoList.add(reviewMapper.toDto(review)));
            mappedRelativeDto.setReviews(reviewDtoList);
        }

        // map students
        List<Student> students = relative.getStudents();
        if (students != null && students.size() > 0) {
            List<StudentDto> studentDtoList = new ArrayList<>();
            students.forEach(student -> studentDtoList.add(studentMapper.toDto(student)));
            mappedRelativeDto.setStudents(studentDtoList);
        }
        return mappedRelativeDto;
    }

    @Override
    public Relative toEntity(RelativeDto relativeDto) {
        Relative mappedRelative = relativeMapper.toEntity(relativeDto);

        // map addresses
        List<AddressDto> addressDtoList = relativeDto.getAddresses();
        if (addressDtoList != null && addressDtoList.size() > 0) {
            List<Address> addresses = new ArrayList<>();
            addressDtoList.forEach(addressDto -> addresses.add(addressMapper.toEntity(addressDto)));
            mappedRelative.setAddresses(addresses);
        }

        // map review
        List<ReviewDto> reviewDtoList = relativeDto.getReviews();
        if (reviewDtoList != null && reviewDtoList.size() > 0) {
            List<Review> reviews = new ArrayList<>();
            reviewDtoList.forEach(reviewDto -> reviews.add(reviewMapper.toEntity(reviewDto)));
            mappedRelative.setReviews(reviews);
        }

        // map student
        List<StudentDto> studentDtoList = relativeDto.getStudents();
        if (studentDtoList != null && studentDtoList.size() > 0) {
            List<Student> students = new ArrayList<>();
            studentDtoList.forEach(studentDto -> students.add(studentMapper.toEntity(studentDto)));
            mappedRelative.setStudents(students);
        }

        return mappedRelative;
    }
}