package ke.or.explorersanddevelopers.lms.service;

import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestEnrollmentDto;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */

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
    StudentDto updateStudent(StudentDto studentDto);

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
    Boolean removeStudentByCode(BigDecimal studentId);

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
    TestEnrollmentDto enrollStudentToTest(BigDecimal studentId, BigDecimal testId);

    /**
     * This method returns a list of students
     *
     * @param pageable - a pagination objection with details like page number and page size
     * @return a list of students
     */
    List<StudentDto> getListOfStudents(Pageable pageable);
}
