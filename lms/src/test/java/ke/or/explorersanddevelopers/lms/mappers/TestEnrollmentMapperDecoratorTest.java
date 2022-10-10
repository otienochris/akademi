package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
import ke.or.explorersanddevelopers.lms.model.entity.TestEnrollment;
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
class TestEnrollmentMapperDecoratorTest {

    private final LocalDate now = LocalDate.now();
    private final Date creationDate = Date.valueOf(now);
    private final Date modificationDate = Date.valueOf(now.plusDays(2));
    private final Question questionEntity = Question.builder().questionId(BigDecimal.valueOf(2)).build();
    private final QuestionDto questionDto = QuestionDto.builder().questionId(BigDecimal.valueOf(2)).build();
    private final ke.or.explorersanddevelopers.lms.model.entity.Test testEntity = ke.or.explorersanddevelopers.lms.model.entity.Test.builder().testId(BigDecimal.ONE).build();
    private final TestDto testDto = TestDto.builder().testId(BigDecimal.ONE).build();
    @Mock
    private TestEnrollmentMapper testEnrollmentMapper;
    @Mock
    private TestMapper testMapper;
    @Mock
    private QuestionMapper questionMapper;
    @InjectMocks
    private TestEnrollmentMapperDecorator testEnrollmentMapperDecorator;
    private TestEnrollment testEnrollmentEntity;
    private TestEnrollmentDto testEnrollmentDto;
    private TestEnrollmentDto partialTestEnrollmentDto;

    @BeforeEach
    void setUp() {

        Long version = 0L;
        List<Question> completedQuestions = List.of(questionEntity);
        BigDecimal testEnrollmentId = BigDecimal.ONE;
        BigDecimal amount = BigDecimal.valueOf(200);
        StatusEnum status = StatusEnum.PENDING;
        double score = 20.0;
        testEnrollmentEntity = TestEnrollment.builder()
                .testEnrollmentId(testEnrollmentId)
                .test(testEntity)
                .amount(amount)
                .status(status)
                .score(score)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .completionDate(null)
                .version(version)
                .completedQuestions(completedQuestions)
                .build();

        partialTestEnrollmentDto = TestEnrollmentDto.builder()
                .testEnrollmentId(testEnrollmentId)
                .test(null)
                .amount(amount)
                .status(status)
                .score(score)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .completionDate(null)
                .version(version)
                .completedQuestions(List.of(questionDto))
                .build();
        testEnrollmentDto = TestEnrollmentDto.builder()
                .testEnrollmentId(testEnrollmentId)
                .test(testDto)
                .amount(amount)
                .status(status)
                .score(score)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .completionDate(null)
                .version(version)
                .completedQuestions(List.of(questionDto))
                .build();
    }

    @Test
    void toDto() {
        given(testEnrollmentMapper.toDto(any())).willReturn(partialTestEnrollmentDto);
        given(testMapper.toDto(any())).willReturn(testDto);
        given(questionMapper.toDto(any())).willReturn(questionDto);

        TestEnrollmentDto actualResponse = testEnrollmentMapperDecorator.toDto(testEnrollmentEntity);

        assertThat(actualResponse).isEqualTo(testEnrollmentDto);
    }
}