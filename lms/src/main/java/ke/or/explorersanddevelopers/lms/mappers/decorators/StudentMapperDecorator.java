package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.*;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.model.entity.*;
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
    private AddressMapper addressMapper;

    @Autowired
    private CertificateMapper certificateMapper;

    @Autowired
    @Qualifier("delegate")
    private RelativeMapper relativeMapper;

    @Autowired
//    @Qualifier("delegate")
    private OrganizationMapper organizationMapper;

    @Override
    public StudentDto toDto(Student student) {
        StudentDto studentDto = studentMapper.toDto(student);

        // map reviews
        List<Review> reviews = student.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            List<ReviewDto> reviewDtoList = new ArrayList<>();
            reviews.forEach(review -> reviewDtoList.add(reviewMapper.toDto(review)));
            studentDto.setReviews(reviewDtoList);
        }

        // map addresses
        List<Address> addresses = student.getAddresses();
        if (addresses != null && !addresses.isEmpty()) {
            List<AddressDto> addressDtoList = new ArrayList<>();
            addresses.forEach(address -> addressDtoList.add(addressMapper.toDto(address)));
            studentDto.setAddresses(addressDtoList);
        }

        // map certificates
        List<Certificate> certificates = student.getCertificates();
        if (certificates != null && !certificates.isEmpty()) {
            List<CertificateDto> certificateDtoList = new ArrayList<>();
            certificates.forEach(certificate -> certificateDtoList.add(certificateMapper.toDto(certificate)));
            studentDto.setCertificates(certificateDtoList);
        }

        // map relatives
        List<Relative> relatives = student.getRelatives();
        if (relatives != null && !relatives.isEmpty()) {
            List<RelativeDto> relativeDtoList = new ArrayList<>();
            relatives.forEach(relative -> relativeDtoList.add(relativeMapper.toDto(relative)));
            studentDto.setRelatives(relativeDtoList);
        }

        // map organizations
        List<Organization> organizations = student.getOrganizations();
        if (organizations != null && !organizations.isEmpty()) {
            List<OrganizationDto> organizationDtoList = new ArrayList<>();
            organizations.forEach(organization -> organizationDtoList.add(organizationMapper.toDto(organization)));
            studentDto.setOrganizations(organizationDtoList);
        }

        return studentDto;
    }

    @Override
    public Student toEntity(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);

        // map reviews
        List<ReviewDto> reviewDtoList = studentDto.getReviews();
        if (reviewDtoList != null && !reviewDtoList.isEmpty()) {
            List<Review> reviews = new ArrayList<>();
            reviewDtoList.forEach(reviewDto -> reviews.add(reviewMapper.toEntity(reviewDto)));
            student.setReviews(reviews);
        }

        // map addresses
        List<AddressDto> addressDtoList = studentDto.getAddresses();
        if (addressDtoList != null && !addressDtoList.isEmpty()) {
            List<Address> addresses = new ArrayList<>();
            addressDtoList.forEach(address -> addresses.add(addressMapper.toEntity(address)));
            student.setAddresses(addresses);
        }

        // map certificates
        List<CertificateDto> certificateDtoList = studentDto.getCertificates();
        if (certificateDtoList != null && !certificateDtoList.isEmpty()) {
            List<Certificate> certificates = new ArrayList<>();
            certificateDtoList.forEach(certificate -> certificates.add(certificateMapper.toEntity(certificate)));
            student.setCertificates(certificates);
        }

        // map relatives
        List<RelativeDto> relatives = studentDto.getRelatives();
        if (relatives != null && !relatives.isEmpty()) {
            List<Relative> relativeList = new ArrayList<>();
            relatives.forEach(relativeDto -> relativeList.add(relativeMapper.toEntity(relativeDto)));
            student.setRelatives(relativeList);
        }

        // map organizations
        List<OrganizationDto> organizationDtoList = studentDto.getOrganizations();
        if (organizationDtoList != null && !organizationDtoList.isEmpty()) {
            List<Organization> organizations = new ArrayList<>();
            organizationDtoList.forEach(organization -> organizations.add(organizationMapper.toEntity(organization)));
            student.setOrganizations(organizations);
        }
        return student;
    }
}
