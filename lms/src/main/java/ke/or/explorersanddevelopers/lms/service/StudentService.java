package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */

@Transactional
public interface StudentService {
    /**
     * This method saves a new student record.
     *
     * @param studentDto - the student details to be saved.
     * @return the saved student record.
     */
    StudentDto saveNewStudent(StudentDto studentDto);

    /**
     * This method updates a student record.
     *
     * @param studentDto - the student object with the current details.
     * @return the updated student record
     */
    StudentDto updateStudent(BigDecimal studentId, StudentDto studentDto);

    /**
     * This method retrieves a student by student record id.
     *
     * @param studentId - the student id of the record to be retrieved.
     * @return the retrieved student record
     */
    StudentDto getStudentByCode(BigDecimal studentId);

    /**
     * Delete a student record by id
     *
     * @param studentId - the id of the student to be removed
     * @return true if the operation was successful
     */
    Boolean deleteStudentByCode(BigDecimal studentId);

    /**
     * This method enrolls a student to a course
     *
     * @param studentId - the id of the student enrolling for the selected course
     * @param courseId  - the id of the course the student is enrolling to
     * @return the course enrollment details
     */
    CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId);

    /**
     * This method enrolls a student to a test.
     *
     * @param studentId - the id of the student enrolling for the test.
     * @param testId    - the id of the test the student is enrolling to.
     * @return the test enrollment details
     */
    TestEnrollmentDto enrollStudentToTest(BigDecimal studentId, BigDecimal courseId, BigDecimal testId);

    /**
     * This method returns a list of students
     *
     * @param pageable - a pagination objection with details like page number and page size
     * @return a list of students
     */
    Set<StudentDto> getListOfStudents(Pageable pageable);

    /**
     * This method submits a review
     *
     * @param studentId - the id of the student submitting the review
     * @param targetID  - the id of the object being reviewed. Either a course or an instructor
     * @return true if review was submitted successfully
     */
    Boolean submitReview(BigDecimal studentId, BigDecimal targetID, ReviewDto reviewDto);

    /**
     * This method adds an address to student
     *
     * @param studentId  - The id of the student receiving the address
     * @param addressDto - the address to be added
     * @return the added address
     */
    AddressDto addAddress(BigDecimal studentId, AddressDto addressDto);

    /**
     * This method retrieves a list of certificates owned by a student
     *
     * @param studentId - a student record Id
     * @return a list of certificates owned by a student
     */
    Set<CertificateDto> retrieveCertificates(BigDecimal studentId);

    /**
     * This method generates a token parents can use to track the student's progress
     *
     * @param studentId - the student for which the token is to be assigned
     * @return a UUID token
     */
    UUID generateToken(BigDecimal studentId);

    /**
     * This method allows a student to submit a completed topic
     *
     * @param studentId - the id of the student submitting the completed topic
     * @param topicId   - the id of the topic being submitted
     * @param courseId  - the id of the topic's course
     * @return the updated course enrollment record
     */
    CourseEnrollmentDto completeTopic(BigDecimal studentId, BigDecimal courseId, BigDecimal topicId);

    /**
     * This method retrieves a student by student email.
     *
     * @param email - the student email of the record to be retrieved.
     * @return the retrieved student record
     */
    StudentDto getStudentByEmail(String email);
}
