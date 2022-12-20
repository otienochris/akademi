package ke.or.explorersanddevelopers.lms.mappers.decorators;

import ke.or.explorersanddevelopers.lms.mappers.QuestionMapper;
import ke.or.explorersanddevelopers.lms.mappers.TestEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.mappers.TestMapper;
import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Question;
import ke.or.explorersanddevelopers.lms.model.entity.Test;
import ke.or.explorersanddevelopers.lms.model.entity.TestEnrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
public class TestEnrollmentMapperDecorator implements TestEnrollmentMapper {

    @Autowired
    @Qualifier("delegate")
    private TestEnrollmentMapper testEnrollmentMapper;

    @Autowired
    @Qualifier("delegate")
    private TestMapper testMapper;

    @Autowired
    @Qualifier("delegate")
    private QuestionMapper questionMapper;

    @Override
    public TestEnrollmentDto toDto(TestEnrollment testEnrollment) {
        TestEnrollmentDto mappedTestEnrollmentDto = testEnrollmentMapper.toDto(testEnrollment);
        Test test = testEnrollment.getTest();
        if (test != null)
            mappedTestEnrollmentDto.setTest(testMapper.toDto(test));

        mappedTestEnrollmentDto.setCompletedQuestions(new HashSet<>());
        Set<Question> completedQuestions = testEnrollment.getCompletedQuestions();
        if (completedQuestions != null && !completedQuestions.isEmpty()) {
            completedQuestions.forEach(question -> mappedTestEnrollmentDto.getCompletedQuestions().add(questionMapper.toDto(question)));
        }

        return mappedTestEnrollmentDto;
    }

    @Override
    public TestEnrollment toEntity(TestEnrollmentDto testEnrollmentDto) {
        TestEnrollment mappedTestEnrollment = testEnrollmentMapper.toEntity(testEnrollmentDto);

        TestDto testDto = testEnrollmentDto.getTest();
        if (testDto != null)
            mappedTestEnrollment.setTest(testMapper.toEntity(testDto));

        mappedTestEnrollment.setCompletedQuestions(new HashSet<>());
        Set<QuestionDto> completedQuestions = testEnrollmentDto.getCompletedQuestions();
        if (completedQuestions != null && !completedQuestions.isEmpty()) {
            completedQuestions.forEach(question -> mappedTestEnrollment.getCompletedQuestions().add(questionMapper.toEntity(question)));
        }
        return mappedTestEnrollment;
    }
}
