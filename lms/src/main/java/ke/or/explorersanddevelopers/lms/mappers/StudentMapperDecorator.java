package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.CertificateDto;
import ke.or.explorersanddevelopers.lms.model.dto.ReviewDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import ke.or.explorersanddevelopers.lms.model.entity.Certificate;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

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
//    @Qualifier("delegate")
    private AddressMapper addressMapper;

    @Autowired
    private CertificateMapper certificateMapper;

    @Override
    public StudentDto toDto(Student student) {
        StudentDto studentDto = studentMapper.toDto(student);

        List<Review> reviews = student.getReviews();
        if (reviews != null && reviews.size() > 0){
            List<ReviewDto> reviewDtoList = new ArrayList<>();
            reviews.forEach(review -> reviewDtoList.add(reviewMapper.toDto(review)));
            studentDto.setReviews(reviewDtoList);
        }

        List<Address> addresses = student.getAddresses();
        if (addresses != null && addresses.size() > 0){
            List<AddressDto> addressDtoList = new ArrayList<>();
            addresses.forEach(address -> addressDtoList.add(addressMapper.toDto(address)));
            studentDto.setAddresses(addressDtoList);
        }

        List<Certificate> certificates = student.getCertificates();
        if (certificates != null && certificates.size() > 0){
            List<CertificateDto> certificateDtoList = new ArrayList<>();
            certificates.forEach(certificate -> certificateDtoList.add(certificateMapper.toDto(certificate)));
            studentDto.setCertificates(certificateDtoList);
        }
        return studentDto;
    }

    @Override
    public Student toEntity(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);

        List<ReviewDto> reviewDtoList = studentDto.getReviews();
        if (reviewDtoList != null && reviewDtoList.size() > 0){
            List<Review> reviews = new ArrayList<>();
            reviewDtoList.forEach(reviewDto -> reviews.add(reviewMapper.toEntity(reviewDto)));
            student.setReviews(reviews);
        }

        List<AddressDto> addressDtoList = studentDto.getAddresses();
        if (addressDtoList != null && addressDtoList.size() > 0){
            List<Address> addresses = new ArrayList<>();
            addressDtoList.forEach(address -> addresses.add(addressMapper.toEntity(address)));
            student.setAddresses(addresses);
        }

        List<CertificateDto> certificateDtoList = studentDto.getCertificates();
        if (certificateDtoList != null && certificateDtoList.size() > 0){
            List<Certificate> certificates = new ArrayList<>();
            certificateDtoList.forEach(certificate -> certificates.add(certificateMapper.toEntity(certificate)));
            student.setCertificates(certificates);
        }
        return student;
    }
}
