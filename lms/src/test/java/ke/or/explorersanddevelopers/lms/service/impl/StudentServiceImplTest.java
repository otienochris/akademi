package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.mappers.StudentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @InjectMocks
    private StudentServiceImpl studentService;
    private StudentDto newStudentDto;
    private StudentDto expectedResponse;
    private Student studentEntity;

    @BeforeEach
    void setUp() {
        String countryCode = "KE";
        String email = "abc@xyz.com";
        UUID emailVerificationCode = UUID.randomUUID();
        long version = 0L;
        boolean isAccountDisabled = false;
        String firstName = "Christopher";
        String lastName = "Otieno";
        BigDecimal studentId = BigDecimal.ONE;
        LocalDate now = LocalDate.now();
        Date creationDate = Date.valueOf(now);
        Date modificationDate = Date.valueOf(now.plusDays(10).toString());

        newStudentDto = StudentDto.builder()
                .studentId(null)
                .certificates(new ArrayList<>())
                .countryCode(countryCode)
                .creationDate(null)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(null)
                .version(version)
                .addresses(new ArrayList<>())
                .reviews(new ArrayList<>())
                .isAccountDisabled(isAccountDisabled)
                .build();

        expectedResponse = StudentDto.builder()
                .studentId(studentId)
                .certificates(new ArrayList<>())
                .countryCode(countryCode)
                .creationDate(creationDate)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(modificationDate)
                .version(version)
                .addresses(new ArrayList<>())
                .reviews(new ArrayList<>())
                .isAccountDisabled(isAccountDisabled)
                .build();

        studentEntity = Student.builder()
                .studentId(studentId)
                .certificates(new ArrayList<>())
                .countryCode(countryCode)
                .creationDate(creationDate)
                .email(email)
                .emailVerificationCode(emailVerificationCode)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(modificationDate)
                .version(version)
                .addresses(new ArrayList<>())
                .reviews(new ArrayList<>())
                .isAccountDisabled(isAccountDisabled)
                .build();
    }

    @Test
    void saveNewStudent() {
        given(studentMapper.toEntity(any())).willReturn(studentEntity);
        given(studentRepository.save(any())).willReturn(studentEntity);
        given(studentMapper.toDto(any())).willReturn(expectedResponse);

        StudentDto actualResponse = studentService.saveNewStudent(newStudentDto);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @Disabled
    void updateStudent() {
    }

    @Test
    void getStudentByCode() {
        given(studentRepository.getByStudentId(any())).willReturn(Optional.ofNullable(studentEntity));
        given(studentMapper.toDto(any())).willReturn(expectedResponse);

        StudentDto actualResponse = studentService.getStudentByCode(BigDecimal.ONE);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @Disabled
    void removeStudentByCode() {
    }

    @Test
    @Disabled
    void enrollStudentToCourse() {
    }

    @Test
    @Disabled
    void enrollStudentToTest() {
    }
}