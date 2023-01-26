package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.*;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */
public class StudentMapperDecorator implements StudentMapper {

    @Autowired
    @Qualifier("delegate")
    private StudentMapper studentMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CertificateMapper certificateMapper;

    @Autowired
    @Qualifier("delegate")
    private RelativeMapper relativeMapper;

    @Override
    public StudentDto toDto(Student student) {
        StudentDto studentDto = studentMapper.toDto(student);

        // map reviews
        Set<Review> reviews = student.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            Set<ReviewDto> reviewDtoList = new HashSet<>();
            reviews.forEach(review -> reviewDtoList.add(reviewMapper.toDto(review)));
            studentDto.setReviews(reviewDtoList);
        }

        // map addresses
        Set<Address> addresses = student.getAddresses();
        if (addresses != null && !addresses.isEmpty()) {
            Set<AddressDto> addressDtoList = new HashSet<>();
            addresses.forEach(address -> addressDtoList.add(addressMapper.toDto(address)));
            studentDto.setAddresses(addressDtoList);
        }

        // map certificates
        Set<Certificate> certificates = student.getCertificates();
        if (certificates != null && !certificates.isEmpty()) {
            Set<CertificateDto> certificateDtoList = new HashSet<>();
            certificates.forEach(certificate -> certificateDtoList.add(certificateMapper.toDto(certificate)));
            studentDto.setCertificates(certificateDtoList);
        }

        // map relatives
        Set<Relative> relatives = student.getRelatives();
        if (relatives != null && !relatives.isEmpty()) {
            Set<RelativeDto> relativeDtoList = new HashSet<>();
            relatives.forEach(relative -> relativeDtoList.add(relativeMapper.toDto(relative)));
            studentDto.setRelatives(relativeDtoList);
        }

        return studentDto;
    }

    @Override
    public Student toEntity(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);

        // map reviews
        Set<ReviewDto> reviewDtoList = studentDto.getReviews();
        if (reviewDtoList != null && !reviewDtoList.isEmpty()) {
            Set<Review> reviews = new HashSet<>();
            reviewDtoList.forEach(reviewDto -> reviews.add(reviewMapper.toEntity(reviewDto)));
            student.setReviews(reviews);
        }

        // map addresses
        Set<AddressDto> addressDtoList = studentDto.getAddresses();
        if (addressDtoList != null && !addressDtoList.isEmpty()) {
            Set<Address> addresses = new HashSet<>();
            addressDtoList.forEach(address -> addresses.add(addressMapper.toEntity(address)));
            student.setAddresses(addresses);
        }

        // map certificates
        Set<CertificateDto> certificateDtoList = studentDto.getCertificates();
        if (certificateDtoList != null && !certificateDtoList.isEmpty()) {
            Set<Certificate> certificates = new HashSet<>();
            certificateDtoList.forEach(certificate -> certificates.add(certificateMapper.toEntity(certificate)));
            student.setCertificates(certificates);
        }

        // map relatives
        Set<RelativeDto> relatives = studentDto.getRelatives();
        if (relatives != null && !relatives.isEmpty()) {
            Set<Relative> relativeList = new HashSet<>();
            relatives.forEach(relativeDto -> relativeList.add(relativeMapper.toEntity(relativeDto)));
            student.setRelatives(relativeList);
        }

        return student;
    }
}
