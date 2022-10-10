package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.InstructorDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Monday, 10/10/2022
 */
public interface InstructorService {

    /**
     * This method saves a new instructor record
     *
     * @param instructorDto - the instructor to be saved
     * @return the saved instructor record
     */
    InstructorDto saveNewInstructor(InstructorDto instructorDto);

    /**
     * This method retrieves an instructor by the record's id
     *
     * @param instructorId - the id of the instructor to be retrieved
     * @return the retrieved instructor.
     */
    InstructorDto getInstructorById(BigDecimal instructorId);

    /**
     * This method retrieves a list of instructors
     *
     * @param pageable - the paging object with details like page number and page size
     * @return a list of instructors
     */
    List<InstructorDto> getListOfInstructors(Pageable pageable);

    /**
     * This method deletes an instructor record using an id
     *
     * @param studentId - the id of the student to be deleted
     * @return true if operation was successful
     */
    Boolean deleteInstructorById(BigDecimal studentId);
}
