package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.InstructorDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Monday, 10/10/2022
 */
@Transactional
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
    Set<InstructorDto> getListOfInstructors(Pageable pageable);

    /**
     * This method deletes an instructor record using an id
     *
     * @param studentId - the id of the student to be deleted
     * @return true if operation was successful
     */
    Boolean deleteInstructorById(BigDecimal studentId);

    /**
     * This method saves a new address to an instructor
     *
     * @param instructorId - the instructor getting the new address
     * @return the new instructor details
     */
    InstructorDto addAddress(BigDecimal instructorId, AddressDto addressDto);

    /**
     * This method retrieves an instructor by the record's email
     *
     * @param email - the email of the instructor to be retrieved
     * @return the retrieved instructor.
     */
    InstructorDto getInstructorByEmail(String email);

    /**
     * This method update an instructor record
     *
     * @param studentId     - the student id
     * @param instructorDto - the instructor to be saved
     * @return the saved instructor record
     */
    InstructorDto updateInstructor(BigDecimal studentId, InstructorDto instructorDto);
}
