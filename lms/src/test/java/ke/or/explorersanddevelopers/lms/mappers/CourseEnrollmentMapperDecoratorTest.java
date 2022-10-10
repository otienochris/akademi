package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CourseEnrollmentMapperDecoratorTest {

    // student
    private final Student studentEntity = Student.builder().studentId(BigDecimal.ONE).build();
    private final StudentDto studentDto = StudentDto.builder().studentId(BigDecimal.ONE).build();
    private final Course courseEntity = Course.builder().courseId(BigDecimal.ONE).build();
    private final CourseDto courseDto = CourseDto.builder().courseId(BigDecimal.ONE).build();
    private final List<TestEnrollment> testEnrollmentEntities = List.of(TestEnrollment.builder().testEnrollmentId(BigDecimal.valueOf(101)).build());
    private final List<TestEnrollmentDto> testEnrollmentDtos = List.of(TestEnrollmentDto.builder().testEnrollmentId(BigDecimal.valueOf(101)).build());
    @Mock
    private CourseEnrollmentMapper courseEnrollmentMapper;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private TestEnrollmentMapper testEnrollmentMapper;
    @InjectMocks
    private CourseEnrollmentMapperDecorator courseEnrollmentMapperDecorator;
    private CourseEnrollment courseEnrollmentEntity;
    private CourseEnrollmentDto courseEnrollmentDto;
    private CourseEnrollmentDto partialCourseEnrollmentDto;

    @BeforeEach
    void setUp() {
        StatusEnum pending = StatusEnum.PENDING;
        long version = 0L;
        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal courseEnrollmentId = BigDecimal.ONE;
        List<Topic> completedTopics = List.of(Topic.builder().topicId(BigDecimal.ONE).build());
        List<TopicDto> completedTopicsDtos = List.of(TopicDto.builder().topicId(BigDecimal.ONE).build());
        LocalDate now = LocalDate.now();
        Date creationDate = Date.valueOf(now);
        Date modificationDate = Date.valueOf(now.plusDays(2));
        Date completedTopic = null;

        courseEnrollmentEntity = CourseEnrollment.builder()
                .course(courseEntity)
                .courseEnrollmentId(courseEnrollmentId)
                .testEnrollments(testEnrollmentEntities)
                .courseEnrollmentId(courseEnrollmentId)
                .student(studentEntity)
                .completedTopics(completedTopics)
                .creationDate(creationDate)
                .status(pending)
                .version(version)
                .amount(amount)
                .modificationDate(modificationDate)
                .completionDate(null)
                .build();

        partialCourseEnrollmentDto = CourseEnrollmentDto.builder()
                .course(null)
                .courseEnrollmentId(courseEnrollmentId)
                .testEnrollments(null)
                .courseEnrollmentId(courseEnrollmentId)
                .student(null)
                .completedTopics(completedTopicsDtos)
                .creationDate(creationDate)
                .status(pending)
                .version(version)
                .amount(amount)
                .modificationDate(modificationDate)
                .completionDate(null)
                .build();
        courseEnrollmentDto = CourseEnrollmentDto.builder()
                .course(courseDto)
                .courseEnrollmentId(courseEnrollmentId)
                .testEnrollments(testEnrollmentDtos)
                .courseEnrollmentId(courseEnrollmentId)
                .student(studentDto)
                .completedTopics(completedTopicsDtos)
                .creationDate(creationDate)
                .status(pending)
                .version(version)
                .amount(amount)
                .modificationDate(modificationDate)
                .completionDate(null)
                .build();
    }

    @Test
    void toDto() {
        given(courseEnrollmentMapper.toDto(any())).willReturn(partialCourseEnrollmentDto);
        given(studentMapper.toDto(any())).willReturn(studentDto);
        given(courseMapper.toDto(any())).willReturn(courseDto);
        given(testEnrollmentMapper.toDto(any())).willReturn(TestEnrollmentDto.builder().testEnrollmentId(BigDecimal.valueOf(101)).build());

        CourseEnrollmentDto actualResponse = courseEnrollmentMapperDecorator.toDto(courseEnrollmentEntity);

        assertThat(actualResponse).isEqualTo(courseEnrollmentDto);
    }
}