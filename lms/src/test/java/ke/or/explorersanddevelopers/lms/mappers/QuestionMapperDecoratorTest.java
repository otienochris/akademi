package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.enums.AnswerTypeEnum;
import ke.or.explorersanddevelopers.lms.enums.DifficultyLevelEnum;
import ke.or.explorersanddevelopers.lms.model.dto.AnswerDto;
import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;
import ke.or.explorersanddevelopers.lms.model.entity.Answer;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
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
class QuestionMapperDecoratorTest {

    private final Answer answer = Answer.builder().answerId(BigDecimal.ONE).build();
    private final AnswerDto answerDto = AnswerDto.builder().answerId(BigDecimal.ONE).build();
    private final List<Answer> answers = List.of(answer);
    private final List<AnswerDto> answersDto = List.of(answerDto);
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private AnswerMapper answerMapper;
    @InjectMocks
    private QuestionMapperDecorator questionMapperDecorator;
    private QuestionDto questionDto;
    private Question questionEntity;
    private QuestionDto partialQuestionDto;

    @BeforeEach
    void setUp() {

        DifficultyLevelEnum difficultyLevelEnum = DifficultyLevelEnum.EASY;
        String hint = "Some Hint";
        Long version = 0L;
        LocalDate now = LocalDate.now();
        Date creationDate = Date.valueOf(now);
        Date modificationDate = Date.valueOf(now.plusDays(10));
        AnswerTypeEnum answerType = AnswerTypeEnum.MULTI_CHOICE;
        long period = 20L;
        double successRate = 0.0;
        String question = "What is the question?";
        BigDecimal questionId = BigDecimal.ONE;

        partialQuestionDto = QuestionDto.builder()
                .questionId(questionId)
                .question(question)
                .answers(null)
                .version(version)
                .creationDate(creationDate)
                .answerType(answerType)
                .modificationDate(modificationDate)
                .hint(hint)
                .level(difficultyLevelEnum)
                .period(period)
                .successRate(successRate)
                .build();
        questionDto = QuestionDto.builder()
                .questionId(questionId)
                .question(question)
                .answers(answersDto)
                .version(version)
                .creationDate(creationDate)
                .answerType(answerType)
                .modificationDate(modificationDate)
                .hint(hint)
                .level(difficultyLevelEnum)
                .period(period)
                .successRate(successRate)
                .build();

        questionEntity = Question.builder()
                .questionId(questionId)
                .question(question)
                .answers(answers)
                .version(version)
                .creationDate(creationDate)
                .answerType(answerType)
                .modificationDate(modificationDate)
                .hint(hint)
                .level(difficultyLevelEnum)
                .period(period)
                .successRate(successRate)
                .build();
    }

    @Test
    void toDto() {
        given(questionMapper.toDto(any())).willReturn(partialQuestionDto);
        given(answerMapper.toDto(any())).willReturn(answerDto);

        QuestionDto actualResponse = questionMapperDecorator.toDto(questionEntity);

        assertThat(actualResponse).isEqualTo(questionDto);
    }
}