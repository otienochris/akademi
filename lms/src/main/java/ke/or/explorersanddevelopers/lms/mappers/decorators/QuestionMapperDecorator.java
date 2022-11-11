package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.AnswerMapper;
import ke.or.explorersanddevelopers.lms.mappers.QuestionMapper;
import ke.or.explorersanddevelopers.lms.model.dto.AnswerDto;
import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;
import ke.or.explorersanddevelopers.lms.model.entity.Answer;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
public class QuestionMapperDecorator implements QuestionMapper {

    @Autowired
    @Qualifier("delegate")
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public QuestionDto toDto(Question question) {
        QuestionDto mappedQuestionDto = questionMapper.toDto(question);

        mappedQuestionDto.setAnswers(new ArrayList<>());
        List<Answer> answers = question.getAnswers();
        if (answers != null && !answers.isEmpty()) {
            answers.forEach(answer -> mappedQuestionDto.getAnswers().add(answerMapper.toDto(answer)));
        }
        return mappedQuestionDto;
    }

    @Override
    public Question toEntity(QuestionDto questionDto) {
        Question mappedQuestion = questionMapper.toEntity(questionDto);

        mappedQuestion.setAnswers(new ArrayList<>());
        List<AnswerDto> answers = questionDto.getAnswers();
        if (answers != null && !answers.isEmpty()) {
            answers.forEach(answer -> mappedQuestion.getAnswers().add(answerMapper.toEntity(answer)));
        }

        return mappedQuestion;
    }
}
