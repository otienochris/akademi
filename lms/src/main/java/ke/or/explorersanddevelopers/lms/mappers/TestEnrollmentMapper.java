package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.TestEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.TestEnrollment;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
@Mapper(componentModel = "spring")
@DecoratedWith(TestEnrollmentMapperDecorator.class)
public interface TestEnrollmentMapper {

    TestEnrollmentMapper INSTANCE = Mappers.getMapper(TestEnrollmentMapper.class);

    /**
     * This method maps a test enrollment entity to its equivalent test enrollment dto
     *
     * @param testEnrollment - the test enrollment entity to be mapped
     * @return the mapped test enrollment dto
     */
    @Mappings(value = {
            @Mapping(target = "test", ignore = true)
    })
    TestEnrollmentDto toDto(TestEnrollment testEnrollment);
}
