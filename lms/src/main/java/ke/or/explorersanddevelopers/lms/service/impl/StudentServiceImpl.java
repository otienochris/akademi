package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.exception.DuplicateRecordException;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.CourseEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.mappers.StudentMapper;
import ke.or.explorersanddevelopers.lms.mappers.TestEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.*;
import ke.or.explorersanddevelopers.lms.repositories.CourseEnrollmentRepository;
import ke.or.explorersanddevelopers.lms.repositories.CourseRepository;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import ke.or.explorersanddevelopers.lms.repositories.TestRepository;
import ke.or.explorersanddevelopers.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseRepository courseRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseEnrollmentMapper courseEnrollmentMapper;
    private final TestRepository testRepository;
    private final TestEnrollmentMapper testEnrollmentMapper;

    @Override
    public StudentDto saveNewStudent(StudentDto studentDto) {
        log.info("Saving a new student");
        Student studentEntity = studentMapper.toEntity(studentDto);
        studentEntity.setVersion(null);
        Student savedStudent = studentRepository.save(studentEntity);
        log.info("Student saved successfully");
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public StudentDto getStudentByCode(BigDecimal studentId) {
        log.info("Retrieving a student of id: " + studentId);
        Student student = getStudentByCodeFromDb(studentId);
        log.info("Successfully retrieved a student of id: " + studentId);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {

        return null;
    }

    @Override
    public Boolean removeStudentByCode(BigDecimal studentId) {
        log.info("Deleting a student of student id: " + studentId);
        Student student = getStudentByCodeFromDb(studentId);
        studentRepository.delete(student);
        log.info("Successfully Deleted a student of student id: " + studentId);
        return true;
    }

    @Override
    public CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId) {

        CourseEnrollment courseEnrollment;
        Student student = getStudentByCodeFromDb(studentId);
        Course course = getCourseByCodeFromDb(courseId);

        // check if already enrolled for the course
        Boolean existsByCourseAndStudent = courseEnrollmentRepository.existsByCourseAndStudent(course, student);
        if (existsByCourseAndStudent) {
            String message = "Student of id: " + studentId + " is already enrolled on course of id: " + courseId;
            log.error(message);
            throw new DuplicateRecordException(message);
        } else {
            courseEnrollment = CourseEnrollment.builder()
                    .course(course)
                    .testEnrollments(new ArrayList<>())
                    .student(student)
                    .status(StatusEnum.PENDING)
                    .completedTopics(new ArrayList<>())
                    .build();
            courseEnrollmentRepository.save(courseEnrollment);
        }

        return courseEnrollmentMapper.toDto(courseEnrollment);
    }

    @Override
    public TestEnrollmentDto enrollStudentToTest(BigDecimal studentId, BigDecimal courseId, BigDecimal testId) {

        Student student = getStudentByCodeFromDb(studentId);
        Course course = getCourseByCodeFromDb(courseId);

        CourseEnrollment courseEnrollment = courseEnrollmentRepository.getByCourseAndStudent(course, student).orElseThrow(() -> {
            String message = "The student [" + student + "] has not enrolled for course [" + courseId + "]";
            log.error(message);
            throw new NoSuchRecordException(message);
        });

        List<TestEnrollment> oldTestEnrollments = courseEnrollment.getTestEnrollments();
        if (oldTestEnrollments != null && oldTestEnrollments.size() > 0) {
            oldTestEnrollments.forEach(testEnrollment -> {
                Test test = testEnrollment.getTest();
                if (test != null && Objects.equals(test.getTestId(), testId)) {
                    String message = "The student [" + student + "] has already enrolled for the test [" + courseId + "]";
                    log.error(message);
                    throw new DuplicateRecordException(message);
                }
            });
        }

        Test test = getTestByCodeFromDb(testId);
        TestEnrollment newTestEnrollment = TestEnrollment.builder()
                .amount(BigDecimal.ZERO)
                .test(test)
                .score(0.0)
                .status(StatusEnum.PENDING)
                .build();

        if (oldTestEnrollments == null || oldTestEnrollments.size() == 0) {
            oldTestEnrollments = new ArrayList<>();
        }
        oldTestEnrollments.add(newTestEnrollment);
        courseEnrollment.setTestEnrollments(oldTestEnrollments);


        return testEnrollmentMapper.toDto(newTestEnrollment);
    }

    @Override
    public List<StudentDto> getListOfStudents(Pageable pageable) {
        log.info("Retrieving a list of students");
        List<StudentDto> response = new ArrayList<>();
        studentRepository.findAll(pageable).forEach(student -> response.add(studentMapper.toDto(student)));
        if (response.size() == 0)
            log.warn("Retrieve an empty list of students");
        else
            log.info("Successfully retrieved a list of students");
        return response;
    }

    private Student getStudentByCodeFromDb(BigDecimal studentId) {
        return studentRepository.getByStudentId(studentId).orElseThrow(() -> {
            String message = "Student of id " + studentId + " could not be found.";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }

    private Course getCourseByCodeFromDb(BigDecimal courseId) {
        return courseRepository.getByCourseId(courseId).orElseThrow(() -> {
            String message = "A course with id: " + courseId + " could no be found";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }

    private Test getTestByCodeFromDb(BigDecimal testId) {
        return testRepository.getByTestId(testId).orElseThrow(() -> {
            String message = "A test of id: " + testId + " could not be found! ";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
    }
}