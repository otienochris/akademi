package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.TestEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Test;
import ke.or.explorersanddevelopers.lms.model.entity.TestEnrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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

    @Override
    public TestEnrollmentDto toDto(TestEnrollment testEnrollment) {
        TestEnrollmentDto mappedTestEnrollmentDto = testEnrollmentMapper.toDto(testEnrollment);
        Test test = testEnrollment.getTest();
        if (test != null)
            mappedTestEnrollmentDto.setTest(testMapper.toDto(test));

        return mappedTestEnrollmentDto;
    }
}
