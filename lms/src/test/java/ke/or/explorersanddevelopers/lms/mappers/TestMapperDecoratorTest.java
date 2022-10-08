package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.enums.TestTypeEnum;
import ke.or.explorersanddevelopers.lms.enums.YesOrNoEnum;
import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TestMapperDecoratorTest {

    private final Question questionEntity = Question.builder().questionId(BigDecimal.ONE).build();
    private final QuestionDto questionDto = QuestionDto.builder().questionId(BigDecimal.ONE).build();
    private final Topic topicEntity = Topic.builder().topicId(BigDecimal.ONE).build();
    private final TopicDto topicDto = TopicDto.builder().topicId(BigDecimal.ONE).build();
    @Mock
    private TestMapper testMapper;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private TopicMapper topicMapper;
    @InjectMocks
    private TestMapperDecorator testMapperDecorator;
    private ke.or.explorersanddevelopers.lms.model.entity.Test testEntity;
    private TestDto testDto;
    private TestDto partialTestDto;

    @BeforeEach
    void setUp() {
        Long version = 0L;
        BigDecimal testId = BigDecimal.ONE;
        YesOrNoEnum isScheduled = YesOrNoEnum.Y;
        YesOrNoEnum isOptional = YesOrNoEnum.N;
        LocalDate now = LocalDate.now();
        Date creationDate = Date.valueOf(now);
        Date modificationDate = Date.valueOf(now.plusDays(2));
        Date endDate = Date.valueOf(now.plusMonths(2));
        Date startDate = Date.valueOf(now.plusMonths(1));
        TestTypeEnum testType = TestTypeEnum.EXAM;
        testEntity = ke.or.explorersanddevelopers.lms.model.entity.Test.builder()
                .testId(testId)
                .questions(List.of(questionEntity))
                .isScheduled(isScheduled)
                .isOptional(isOptional)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .testType(testType)
                .endDateAndTime(endDate)
                .version(version)
                .startDateAndTime(startDate)
                .topics(List.of(topicEntity))
                .build();

        partialTestDto = TestDto.builder()
                .testId(testId)
                .questions(new ArrayList<>())
                .isScheduled(isScheduled)
                .isOptional(isOptional)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .testType(testType)
                .endDateAndTime(endDate)
                .version(version)
                .startDateAndTime(startDate)
                .topics(new ArrayList<>())
                .build();
        testDto = TestDto.builder()
                .testId(testId)
                .questions(List.of(questionDto))
                .isScheduled(isScheduled)
                .isOptional(isOptional)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .testType(testType)
                .endDateAndTime(endDate)
                .version(version)
                .startDateAndTime(startDate)
                .topics(List.of(topicDto))
                .build();
    }

    @Test
    void toDto() {
        given(testMapper.toDto(any())).willReturn(partialTestDto);
        given(questionMapper.toDto(any())).willReturn(questionDto);
        given(topicMapper.toDto(any())).willReturn(topicDto);

        TestDto actualResponse = testMapperDecorator.toDto(testEntity);

        assertThat(actualResponse).isEqualTo(testDto);
    }
}