package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Wednesday 26/10/2022
 **/
public interface TestService {

    /**
     * This method creates a test
     *
     * @param testDto - details needed to create a test
     * @return the created test
     */
    TestDto createNewTest(BigDecimal topicId, TestDto testDto);

    /**
     * This method retrieves a test using its id
     *
     * @param testId - required to retrieve  a test
     * @return the retrieved test
     */
    TestDto getTestById(BigDecimal testId);

    /**
     * This method retrieves and edits a test using its id
     *
     * @param testId  - required to retrieve a test
     * @param testDto - test details needed to update  test
     * @return - the updated test
     */
    TestDto edtTestById(BigDecimal testId, TestDto testDto);

    /**
     * This method retrieves a list of all tests
     *
     * @param pageable - a page object of test with useful information such as total pages
     * @return a list of tests
     */
    List<TestDto> getListOfTests(Pageable pageable);

    /**
     * This method deletes a test using its id
     *
     * @param testId - required to delete a test
     * @return true if a test is deleted
     */
    Boolean deleteTestById(BigDecimal testId);

    /**
     * THis method deletes all tests
     *
     * @return true if all tests are deleted
     */
    Boolean deleteAllTests();
}
