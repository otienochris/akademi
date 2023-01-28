package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.exception.DuplicateRecordException;
import ke.or.explorersanddevelopers.lms.mappers.CourseEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.model.entity.*;
import ke.or.explorersanddevelopers.lms.repositories.CourseEnrollmentRepository;
import ke.or.explorersanddevelopers.lms.repositories.CourseRepository;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CourseEnrollmentServiceImplTest {

    public static final BigDecimal COURSE_ENROLLMENT_ID = BigDecimal.TEN;
    public static final long VERSION = 1L;
    public static final String COMPLETED_TOPICS_IDS = "200";
    public static final BigDecimal STUDENT_ID = BigDecimal.valueOf(500);
    public static final BigDecimal COURSE_ID = BigDecimal.valueOf(100);
    public static final BigDecimal SECOND_TOPIC_ID = BigDecimal.valueOf(201);
    public static final BigDecimal FIRST_TOPIC_ID = BigDecimal.valueOf(200);
    public static final BigDecimal FIRST_SUB_TOPIC_ID = BigDecimal.valueOf(300);
    public static final BigDecimal SECOND_SUB_TOPIC_ID = BigDecimal.valueOf(301);
    public static final BigDecimal THIRD_SUB_TOPIC_ID = BigDecimal.valueOf(302);
    public static final String COMPLETED_SUB_TOPICS_IDS = FIRST_SUB_TOPIC_ID + "," + SECOND_SUB_TOPIC_ID;
    private final TopicDto firstTopicDto = TopicDto.builder()
            .topicId(FIRST_TOPIC_ID)
            .subTopics(null)
            .build();
    private final TopicDto secondTopicDto = TopicDto.builder()
            .topicId(SECOND_TOPIC_ID)
            .subTopics(null)
            .build();
    private final LocalDate now = LocalDate.now();
    private final Course courseEntity = Course.builder()
            .courseId(COURSE_ID)
            .topics(null)
            .build();
    private final CourseDto courseDto = CourseDto.builder()
            .courseId(COURSE_ID)
            .topics(null)
            .build();
    // subtopics
    public SubTopic firstSubTopic = SubTopic.builder()
            .subTopicId(FIRST_SUB_TOPIC_ID)
            .build();
    public SubTopic secondSubTopic = SubTopic.builder()
            .subTopicId(SECOND_SUB_TOPIC_ID)
            .build();
    public SubTopic thirdSubTopic = SubTopic.builder()
            .subTopicId(THIRD_SUB_TOPIC_ID)
            .build();
    public SubTopicDto firstSubTopicDto = SubTopicDto.builder()
            .subTopicId(FIRST_SUB_TOPIC_ID)
            .build();
    public SubTopicDto secondSubTopicDto = SubTopicDto.builder()
            .subTopicId(SECOND_SUB_TOPIC_ID)
            .build();
    public SubTopicDto thirdSubTopicDto = SubTopicDto.builder()
            .subTopicId(THIRD_SUB_TOPIC_ID)
            .build();
    // topics
    public Topic firstTopic = Topic.builder()
            .topicId(FIRST_TOPIC_ID)
            .subTopics(Collections.singleton(firstSubTopic))
            .build();
    public Topic secondTopic = Topic.builder()
            .topicId(SECOND_TOPIC_ID)
            .subTopics(null)
            .build();
    @Mock
    private CourseEnrollmentRepository courseEnrollmentRepository;
    @Mock
    private CourseEnrollmentMapper courseEnrollmentMapper;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseEnrollmentServiceImpl courseEnrollmentService;
    private CourseEnrollment sampleCourseEnrollmentEntity;
    private Student studentEntity;
    private CourseEnrollmentDto expectedResponse;


    @BeforeEach
    void setUp() {
        // construct the student entity
        studentEntity = Student.builder()
                .studentId(STUDENT_ID)
                .build();
        StudentDto studentDto = StudentDto.builder()
                .studentId(STUDENT_ID)
                .build();

        // construct the topics for course dto
        firstTopicDto.setSubTopics(Set.of(firstSubTopicDto));
        secondTopicDto.setSubTopics(Set.of(secondSubTopicDto, thirdSubTopicDto));
        courseDto.setTopics(Set.of(firstTopicDto, secondTopicDto));

        // construct the topics for course entity
        firstTopic.setSubTopics(Set.of(firstSubTopic));
        secondTopic.setSubTopics(Set.of(secondSubTopic, thirdSubTopic));
        courseEntity.setTopics(Set.of(firstTopic, secondTopic));

        sampleCourseEnrollmentEntity = CourseEnrollment.builder()
                .course(courseEntity)
                .courseEnrollmentId(COURSE_ENROLLMENT_ID)
                .testEnrollments(null)
                .student(studentEntity)
                .status(StatusEnum.PENDING)
                .amount(BigDecimal.valueOf(100))
                .completedSubTopicsIds(COMPLETED_SUB_TOPICS_IDS)
                .completedTopicsIds(COMPLETED_TOPICS_IDS)
                .version(VERSION)
                .completionDate(null)
                .creationDate(Date.valueOf(now.minusDays(5)))
                .modificationDate(Date.valueOf(now))
                .build();

        Map<BigDecimal, Set<BigDecimal>> completedSubTopicsForDto = new HashMap<>();
        completedSubTopicsForDto.put(FIRST_TOPIC_ID, Set.of(FIRST_SUB_TOPIC_ID));
        completedSubTopicsForDto.put(SECOND_TOPIC_ID, Set.of(SECOND_SUB_TOPIC_ID, THIRD_SUB_TOPIC_ID));

        expectedResponse = CourseEnrollmentDto.builder()
                .course(courseDto)
                .courseEnrollmentId(COURSE_ENROLLMENT_ID)
                .testEnrollments(null)
                .student(studentDto)
                .status(StatusEnum.PENDING)
                .amount(BigDecimal.valueOf(100))
                .completedSubTopicsIds(completedSubTopicsForDto)
                .completedTopicsIds(List.of(FIRST_TOPIC_ID, SECOND_TOPIC_ID))
                .version(VERSION)
                .completionDate(null)
                .creationDate(Date.valueOf(now.minusDays(5)))
                .modificationDate(Date.valueOf(now))
                .build();
    }

    @Test
    void convertListItemsIntoCommaSeperatedString() {
        String actualResponse = CourseEnrollmentServiceImpl.convertListItemsIntoCommaSeperatedString(List.of("1", "2", "3", "4"));

        assertThat(actualResponse).isEqualTo("1,2,3,4");
    }

    @Test
    void enrollStudentToCourse() {
        given(studentRepository.getByStudentId(STUDENT_ID)).willReturn(Optional.ofNullable(studentEntity));
        given(courseRepository.getByCourseId(COURSE_ID)).willReturn(Optional.ofNullable(courseEntity));
        given(courseEnrollmentRepository.existsByCourseAndStudent(courseEntity, studentEntity)).willReturn(false);
        given(courseEnrollmentRepository.save(any())).willReturn(sampleCourseEnrollmentEntity);
        given(courseEnrollmentMapper.toDto(any())).willReturn(expectedResponse);

        CourseEnrollmentDto actualResponse = courseEnrollmentService.enrollStudentToCourse(STUDENT_ID, COURSE_ID);

        assertThat(actualResponse.getStatus()).isEqualTo(StatusEnum.PENDING);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void getCourseEnrollments() {
        given(courseEnrollmentRepository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(Collections.singletonList(sampleCourseEnrollmentEntity)));
        given(courseEnrollmentMapper.toDto(any())).willReturn(expectedResponse);

        List<CourseEnrollmentDto> actualResponse = courseEnrollmentService.getCourseEnrollments(PageRequest.of(0, 10));

        assertThat(actualResponse).isEqualTo(Collections.singletonList(expectedResponse));
    }

    @Test
    void getCourseEnrollmentByStudentId() {
        given(studentRepository.getByStudentId(STUDENT_ID)).willReturn(Optional.ofNullable(studentEntity));
        given(courseEnrollmentRepository.findByStudent(studentEntity)).willReturn(Collections.singletonList(sampleCourseEnrollmentEntity));
        given(courseEnrollmentMapper.toDto(sampleCourseEnrollmentEntity)).willReturn(expectedResponse);

        List<CourseEnrollmentDto> actualResponse = courseEnrollmentService.getCourseEnrollmentByStudentId(STUDENT_ID);

        assertThat(actualResponse).isEqualTo(Collections.singletonList(expectedResponse));
    }

    @Test
    void completeSubTopic() {

        given(courseEnrollmentRepository.findByCourseEnrollmentId(COURSE_ENROLLMENT_ID)).willReturn(Optional.ofNullable(sampleCourseEnrollmentEntity));
        given(courseEnrollmentRepository.save(any())).willReturn(sampleCourseEnrollmentEntity);
        given(courseEnrollmentMapper.toDto(any())).willReturn(expectedResponse);


        CourseEnrollmentDto actualResponse = courseEnrollmentService.completeSubTopic(COURSE_ENROLLMENT_ID, THIRD_SUB_TOPIC_ID);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void completeSubTopic_throwsExceptionIfSubtopicAlreadyCompleted() {
        given(courseEnrollmentRepository.findByCourseEnrollmentId(COURSE_ENROLLMENT_ID)).willReturn(Optional.ofNullable(sampleCourseEnrollmentEntity));

        assertThatThrownBy(() -> courseEnrollmentService.completeSubTopic(COURSE_ENROLLMENT_ID, FIRST_SUB_TOPIC_ID)).isInstanceOf(DuplicateRecordException.class).hasMessageContaining("Student already completed this subtopic");

    }
}