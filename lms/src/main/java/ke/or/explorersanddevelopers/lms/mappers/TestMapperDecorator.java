package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
import ke.or.explorersanddevelopers.lms.model.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */


public class TestMapperDecorator implements TestMapper {

    @Autowired
    @Qualifier("delegate")
    private TestMapper testMapper;

    @Autowired
    @Qualifier("delegate")
    private QuestionMapper questionMapper;

    @Override
    public TestDto toDto(Test test) {
        TestDto mappedTestDto = testMapper.toDto(test);

        // TODO: set questions
        mappedTestDto.setQuestions(new ArrayList<>());
        List<Question> questions = test.getQuestions();
        if (questions != null && questions.size() > 0) {
            questions.forEach(question -> mappedTestDto.getQuestions().add(questionMapper.toDto(question)));
        }
        return mappedTestDto;
    }
}
