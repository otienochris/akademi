package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.StudentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.dto.TestEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import ke.or.explorersanddevelopers.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        Student student = studentRepository.getByStudentId(studentId).orElseThrow(() -> {
            String message = "Student of id " + studentId + " could not be found.";
            log.error(message);
            throw new NoSuchRecordException(message);
        });
        log.info("Successfully retrieved a student of id: " + studentId);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        return null;
    }

    @Override
    public Boolean removeStudentByCode(BigDecimal studentId) {
        return null;
    }

    @Override
    public CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId) {
        return null;
    }

    @Override
    public TestEnrollmentDto enrollStudentToTest(BigDecimal studentId, BigDecimal testId) {
        return null;
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
}
