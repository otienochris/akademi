package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.exception.DuplicateRecordException;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.CourseEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.mappers.StudentMapper;
import ke.or.explorersanddevelopers.lms.mappers.TestEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.model.entity.*;
import ke.or.explorersanddevelopers.lms.repositories.*;
import ke.or.explorersanddevelopers.lms.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    CourseDto course = CourseDto.builder().courseId(BigDecimal.TEN).build();
    Course courseEntity = Course.builder().courseId(BigDecimal.TEN).build();
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private CourseEnrollmentMapper courseEnrollmentMapper;
    @Mock
    private CourseEnrollmentRepository courseEnrollmentRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private StudentServiceImpl studentService;
    private StudentDto newStudentDto;
    private StudentDto expectedResponse;
    private Student studentEntity;
    @Mock
    private TestEnrollmentMapper testEnrollmentMapper;
    @Mock
    private TestRepository testRepository;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;

    private CourseEnrollmentDto courseEnrollmentResponse;
    private CourseEnrollment courseEnrollmentEntity;
    private TestEnrollmentDto testEnrollmentResponseDto;


    @BeforeEach
    void setUp() {
        String countryCode = "KE";
        String email = "abc@xyz.com";
        UUID emailVerificationCode = UUID.randomUUID();
        Long version = 0L;
        boolean isAccountDisabled = false;
        String firstName = "Christopher";
        String lastName = "Otieno";
        BigDecimal studentId = BigDecimal.ONE;
        LocalDate now = LocalDate.now();
        Date creationDate = Date.valueOf(now);
        Date modificationDate = Date.valueOf(now.plusDays(10).toString());

        newStudentDto = StudentDto.builder()
                .studentId(null)
                .certificates(new HashSet<>())
                .countryCode(countryCode)
                .creationDate(null)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(null)
                .version(version)
                .addresses(new HashSet<>())
                .reviews(new HashSet<>())
                .isAccountDisabled(isAccountDisabled)
                .build();

        expectedResponse = StudentDto.builder()
                .studentId(studentId)
                .certificates(new HashSet<>())
                .countryCode(countryCode)
                .creationDate(creationDate)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(modificationDate)
                .version(version)
                .addresses(new HashSet<>())
                .reviews(new HashSet<>())
                .isAccountDisabled(isAccountDisabled)
                .build();

        studentEntity = Student.builder()
                .studentId(studentId)
                .certificates(new HashSet<>())
                .countryCode(countryCode)
                .creationDate(creationDate)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .modificationDate(modificationDate)
                .version(version)
                .addresses(new HashSet<>())
                .reviews(new HashSet<>())
                .build();

        // course enrollment

//        Set<TopicDto> completedTopics = Set.of();
        Map<BigDecimal, Set<BigDecimal>> completedTopics = new HashMap<>();
        Set<TestEnrollmentDto> testEnrollments = Set.of();
        StatusEnum status = StatusEnum.PENDING;
        courseEnrollmentResponse = CourseEnrollmentDto.builder()
                .courseEnrollmentId(BigDecimal.valueOf(1))
                .student(newStudentDto)
                .course(course)
                .amount(BigDecimal.valueOf(100))
                .completedSubTopicsIds(completedTopics)
                .completionDate(null)
                .testEnrollments(testEnrollments)
                .status(status)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .version(version)
                .build();

        BigDecimal courseEnrollmentId = BigDecimal.valueOf(1);
        Topic topicDto = Topic.builder().topicId(BigDecimal.ONE).build();
        TestEnrollment testEnrollmentEntity = TestEnrollment.builder().testEnrollmentId(BigDecimal.ONE).build();
        courseEnrollmentEntity = CourseEnrollment.builder()
                .courseEnrollmentId(courseEnrollmentId)
                .student(studentEntity)
                .course(courseEntity)
                .amount(BigDecimal.valueOf(100))
                .completedTopicsIds("1")
                .completionDate(null)
                .testEnrollments(Set.of(testEnrollmentEntity))
                .status(status)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .version(version)
                .build();

        // test enrollment
        BigDecimal testEnrollmentId = BigDecimal.valueOf(2);
        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal testId = BigDecimal.valueOf(11);
        TestDto test = TestDto.builder().testId(testId).build();
        Double score = 0.0;

        QuestionDto questionEntity = QuestionDto.builder().questionId(BigDecimal.ONE).build();
        testEnrollmentResponseDto = TestEnrollmentDto.builder()
                .testEnrollmentId(testEnrollmentId)
                .amount(amount)
                .completedQuestions(Set.of(questionEntity))
                .completionDate(null)
                .test(test)
                .status(StatusEnum.PENDING)
                .score(score)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .version(version)
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
        String email = "updated@gmail.moc";
        newStudentDto.setEmail(email);
        expectedResponse.setEmail(email);

        given(studentRepository.getByStudentId(any())).willReturn(Optional.ofNullable(studentEntity));
        given(studentRepository.save(any())).willReturn(studentEntity);
        given(studentMapper.toDto(any())).willReturn(expectedResponse);

        StudentDto actualResponse = studentService.updateStudent(BigDecimal.ONE, newStudentDto);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void getStudentByCode() {
        given(studentRepository.getByStudentId(any())).willReturn(Optional.ofNullable(studentEntity));
        given(studentMapper.toDto(any())).willReturn(expectedResponse);

        StudentDto actualResponse = studentService.getStudentByCode(BigDecimal.ONE);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void getStudentByCode_throwsException() {
        given(studentRepository.getByStudentId(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentByCode(BigDecimal.ONE)).isInstanceOf(NoSuchRecordException.class);
    }

    @Test
    void deleteStudentByCode() {
        given(studentRepository.getByStudentId(any())).willReturn(Optional.ofNullable(studentEntity));

        Boolean actualResponse = studentService.deleteStudentByCode(BigDecimal.ONE);

        then(studentRepository).should(times(1)).delete(any());
        assertThat(actualResponse).isTrue();
    }

    @Test
    void enrollStudentToCourse() {
        given(studentRepository.getByStudentId(any())).willReturn(Optional.ofNullable(studentEntity));
        given(courseRepository.getByCourseId(any())).willReturn(Optional.ofNullable(courseEntity));
        given(courseEnrollmentRepository.existsByCourseAndStudent(any(), any())).willReturn(false);
        given(courseEnrollmentRepository.save(any())).willReturn(courseEnrollmentEntity);
        given(courseEnrollmentMapper.toDto(any())).willReturn(courseEnrollmentResponse);

        CourseEnrollmentDto actualResponse = studentService.enrollStudentToCourse(BigDecimal.ONE, BigDecimal.TEN);

        assertThat(actualResponse).isEqualTo(courseEnrollmentResponse);
    }

    @Test
    void enrollStudentToCourse_throwsExceptionWhenStudentHasCourse() {
        given(studentRepository.getByStudentId(any())).willReturn(Optional.ofNullable(studentEntity));
        given(courseRepository.getByCourseId(any())).willReturn(Optional.ofNullable(courseEntity));
        given(courseEnrollmentRepository.existsByCourseAndStudent(any(), any())).willReturn(true);

        assertThatThrownBy(() -> studentService.enrollStudentToCourse(BigDecimal.ONE, BigDecimal.TEN)).isInstanceOf(DuplicateRecordException.class);

    }

    @Test
    void enrollStudentToTest() {
        given(studentRepository.getByStudentId(any())).willReturn(Optional.ofNullable(studentEntity));
        given(courseRepository.getByCourseId(any())).willReturn(Optional.ofNullable(courseEntity));
        given(courseEnrollmentRepository.getByCourseAndStudent(any(), any())).willReturn(Optional.ofNullable(CourseEnrollment.builder().build()));
        given(testRepository.getByTestId(any())).willReturn(Optional.ofNullable(ke.or.explorersanddevelopers.lms.model.entity.Test.builder().build()));
        given(testEnrollmentMapper.toDto(any())).willReturn(testEnrollmentResponseDto);

        TestEnrollmentDto actualResponse = studentService.enrollStudentToTest(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.valueOf(2));

        assertThat(actualResponse).isEqualTo(testEnrollmentResponseDto);
    }


    @Test
    void getListOfStudents() {
        given(studentRepository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(List.of(studentEntity)));
        given(studentMapper.toDto(any())).willReturn(expectedResponse);

        Set<StudentDto> actualResponse = studentService.getListOfStudents(PageRequest.of(0, 10));

        assertThat(actualResponse).isEqualTo(Set.of(expectedResponse));
    }
}