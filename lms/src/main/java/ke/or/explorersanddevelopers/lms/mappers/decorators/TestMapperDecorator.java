package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.QuestionMapper;
import ke.or.explorersanddevelopers.lms.mappers.TestMapper;
import ke.or.explorersanddevelopers.lms.mappers.TopicMapper;
import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
import ke.or.explorersanddevelopers.lms.model.entity.Test;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
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

    @Autowired
    @Qualifier("delegate")
    private TopicMapper topicMapper;

    @Override
    public TestDto toDto(Test test) {
        TestDto mappedTestDto = testMapper.toDto(test);

        mappedTestDto.setQuestions(new ArrayList<>()); // initiale
        List<Question> questions = test.getQuestions();
        if (questions != null && !questions.isEmpty()) {
            questions.forEach(question -> mappedTestDto.getQuestions().add(questionMapper.toDto(question)));
        }

        mappedTestDto.setTopics(new ArrayList<>());
        List<Topic> topics = test.getTopics();
        if (topics != null && !topics.isEmpty()) {
            topics.forEach(topic -> mappedTestDto.getTopics().add(topicMapper.toDto(topic)));
        }

        return mappedTestDto;
    }

    @Override
    public Test toEntity(TestDto testDto) {
        Test mappedTest = testMapper.toEntity(testDto);

        mappedTest.setQuestions(new ArrayList<>()); // initiale
        List<QuestionDto> questions = testDto.getQuestions();
        if (questions != null && !questions.isEmpty()) {
            questions.forEach(question -> mappedTest.getQuestions().add(questionMapper.toEntity(question)));
        }

        mappedTest.setTopics(new ArrayList<>());
        List<TopicDto> topics = testDto.getTopics();
        if (topics != null && !topics.isEmpty()) {
            topics.forEach(topic -> mappedTest.getTopics().add(topicMapper.toEntity(topic)));
        }
        return mappedTest;
    }
}
