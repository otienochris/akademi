package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;

import java.math.BigDecimal;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 11/11/2022
 */
public interface QuestionService {

    /**
     * This method saves a new question
     *
     * @param questionDto - the question to be saved
     * @return the saved question object
     */
    QuestionDto saveQuestion(QuestionDto questionDto);

    /**
     * This method retrieves a question by id
     *
     * @param questionId - the id of the question to be retrieved
     * @return the requested question
     */
    QuestionDto getQuestionById(BigDecimal questionId);
}
