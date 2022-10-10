package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.CertificateDto;
import ke.or.explorersanddevelopers.lms.model.dto.ReviewDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Address;
import ke.or.explorersanddevelopers.lms.model.entity.Certificate;
import ke.or.explorersanddevelopers.lms.model.entity.Review;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class StudentMapperDecoratorTest {
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private ReviewMapper reviewMapper;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private CertificateMapper certificateMapper;

    @InjectMocks
    private StudentMapperDecorator studentMapperDecorator;
    private Student studentEntity;
    private StudentDto studentDto;
    private StudentDto partialStudentDto;

    private final BigDecimal randomUUID = BigDecimal.ONE;
    private final ReviewDto reviewDto = ReviewDto.builder().reviewId(randomUUID).build();
    private final AddressDto addressDto = AddressDto.builder().addressId(randomUUID).build();
    private final CertificateDto certificateDto = CertificateDto.builder().certificateId(randomUUID).build();
    private final Address address = Address.builder().addressId(randomUUID).build();
    private final Review review = Review.builder().reviewId(randomUUID).build();
    private final Certificate certificate = Certificate.builder().certificateId(randomUUID).build();
    private Student partialStudentEntity;


    @BeforeEach
    void setup() {
        BigDecimal studentId = BigDecimal.TEN;
        String countryCode = "KE";
        LocalDate now = LocalDate.now();
        Date creationDate = Date.valueOf(now);
        String email = "abc@xyz.com";
        long version = 0L;
        UUID emailVerificationCode = UUID.randomUUID();
        String firstName = "Christopher";
        String lastName = "Otieno";
        Date modificationDate = Date.valueOf(now.plusDays(10));
        boolean isAccountDisabled = false;
        String password = "pass";

        List<CertificateDto> certificateDtoList = new ArrayList<>();
        certificateDtoList.add(certificateDto);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        reviewDtoList.add(reviewDto);
        List<AddressDto> addressDtoList = new ArrayList<>();
        addressDtoList.add(addressDto);

        ArrayList<Address> addresses = new ArrayList<>();

        addresses.add(address);
        ArrayList<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate);
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);

        partialStudentEntity = Student.builder()
                .studentId(studentId)
                .countryCode(countryCode)
                .creationDate(creationDate)
                .email(email)
                .version(version)
                .emailVerificationCode(emailVerificationCode)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(modificationDate)
                .addresses(new ArrayList<>())
                .certificates(new ArrayList<>())
                .reviews(new ArrayList<>())
                .isAccountDisabled(isAccountDisabled)
                .password(password)
                .build();
        studentEntity = Student.builder()
                .studentId(studentId)
                .countryCode(countryCode)
                .creationDate(creationDate)
                .email(email)
                .version(version)
                .emailVerificationCode(emailVerificationCode)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(modificationDate)
                .addresses(addresses)
                .certificates(certificates)
                .reviews(reviews)
                .isAccountDisabled(isAccountDisabled)
                .password(password)
                .build();


        partialStudentDto = StudentDto.builder()
                .studentId(studentId)
                .countryCode(countryCode)
                .creationDate(creationDate)
                .email(email)
                .version(version)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(modificationDate)
                .addresses(new ArrayList<>())
                .certificates(new ArrayList<>())
                .reviews(new ArrayList<>())
                .isAccountDisabled(isAccountDisabled)
                .build();
        studentDto =  StudentDto.builder()
                .studentId(studentId)
                .countryCode(countryCode)
                .creationDate(creationDate)
                .email(email)
                .version(version)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(modificationDate)
                .addresses(addressDtoList)
                .certificates(certificateDtoList)
                .reviews(reviewDtoList)
                .isAccountDisabled(isAccountDisabled)
                .build();
    }

    @Test
    void toDto() {
        given(studentMapper.toDto(any())).willReturn(partialStudentDto);
        given(reviewMapper.toDto(any())).willReturn(reviewDto);
        given(addressMapper.toDto(any())).willReturn(addressDto);
        given(certificateMapper.toDto(any())).willReturn(certificateDto);

        StudentDto actualResponse = studentMapperDecorator.toDto(studentEntity);

        assertThat(actualResponse).isEqualTo(studentDto);
    }

    @Test
    void toEntity() {

        given(studentMapper.toEntity(any())).willReturn(partialStudentEntity);
        given(reviewMapper.toEntity(any())).willReturn(review);
        given(addressMapper.toEntity(any())).willReturn(address);
        given(certificateMapper.toEntity(any())).willReturn(certificate);

        Student actualResponse = studentMapperDecorator.toEntity(studentDto);

        assertThat(actualResponse).isEqualTo(studentEntity);
    }
}