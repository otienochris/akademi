package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.TestMapper;
import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import ke.or.explorersanddevelopers.lms.model.entity.SubTopic;
import ke.or.explorersanddevelopers.lms.model.entity.Test;
import ke.or.explorersanddevelopers.lms.model.entity.Topic;
import ke.or.explorersanddevelopers.lms.repositories.TestRepository;
import ke.or.explorersanddevelopers.lms.repositories.TopicRepository;
import ke.or.explorersanddevelopers.lms.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Wednesday 26/10/2022
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    private final TopicRepository topicRepository;

    private final TestMapper testMapper;

    @Override
    public TestDto createNewTest(BigDecimal topicId, TestDto testDto) {
        log.info("Creating a new test");

        //check if the topic to be mapped to the test exists
        Topic topic = getTopicById(topicId);

        //bind the test to the topic
        Test testEntity  = testMapper.toEntity(testDto);
        List<Topic> associatedTopic = testEntity.getTopics();
        if(associatedTopic == null)
            testEntity.setTopics(new ArrayList<>());
        testEntity.getTopics().add(topic);

        Test createdTest = testRepository.save(testEntity);

        //bind the topic to the test
        Set<Test> testList = topic.getTests();
        if (testList == null)
            topic.setTests(new HashSet<>());
        topic.getTests().add(createdTest);
        topicRepository.save(topic);

        log.info("Successfully created a test");

        return testMapper.toDto(createdTest);
    }

    private Topic getTopicById(BigDecimal topicId) {
        return topicRepository.getByTopicId(topicId)
                .orElseThrow(() -> new NoSuchRecordException("Topic with id: " + topicId + " does not exist"));
    }

    @Override
    public TestDto getTestById(BigDecimal testId) {
        log.info("Retrieving test with id: " + testId);

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> {
                    String message = "Test with id: " + testId + " does not exist";
                    log.warn(message);
                    throw new NoSuchRecordException(message);
                });
        log.info("Successfully retrieved the test");

        return testMapper.toDto(test);
    }

    @Override
    public TestDto edtTestById(BigDecimal testId, TestDto testDto) {
        log.info("Retrieving  test with id: " + testId + " for editing");

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> {
                    String message = "Test with id: " + testId + "does not exist";
                    log.warn(message);
                    throw new NoSuchRecordException(message);
                        }
                );
        log.info("Test successfully retrieved, proceeding to editing");

        test.setTestType(testDto.getTestType());
        test.setIsOptional(testDto.getIsOptional());
        test.setIsScheduled(testDto.getIsScheduled());
        test.setStartDateAndTime(testDto.getStartDateAndTime());
        test.setEndDateAndTime(testDto.getEndDateAndTime());

        final Test updatedTest = testRepository.save(test);

        return testMapper.toDto(updatedTest);
    }

    @Override
    public List<TestDto> getListOfTests(Pageable pageable) {
        log.info("Retrieving a list of tests");

        List<TestDto> testDtoList = new ArrayList<>();

        testRepository.findAll((Sort) pageable).forEach(test -> testDtoList.add(testMapper.toDto(test)));

        if(testDtoList.size() == 0)
            log.warn("Retrieving an empty list of test");
        else
            log.info("Successfully retrieved a list of test");

        return testDtoList;
    }

    @Override
    public Boolean deleteTestById(BigDecimal testId) {
        log.info("Deleting test with id:" + testId);

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> {
                    String message = "Test with id: " + testId + " does not exist";
                    log.warn(message);
                    throw new NoSuchRecordException(message);
                });

        testRepository.delete(test);
        log.info("Test successfully deleted");
        return true;
    }

    @Override
    public Boolean deleteAllTests() {
        log.warn("Deleting all the tests");
        testRepository.deleteAll();
        log.info("Deleted all tests");
        return true;
    }
}
