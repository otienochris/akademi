package ke.or.explorersanddevelopers.lms.service.impl;

import ke.or.explorersanddevelopers.lms.enums.StatusEnum;
import ke.or.explorersanddevelopers.lms.exception.DuplicateRecordException;
import ke.or.explorersanddevelopers.lms.exception.NoSuchRecordException;
import ke.or.explorersanddevelopers.lms.mappers.CourseEnrollmentMapper;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.repositories.CourseEnrollmentRepository;
import ke.or.explorersanddevelopers.lms.repositories.CourseRepository;
import ke.or.explorersanddevelopers.lms.repositories.StudentRepository;
import ke.or.explorersanddevelopers.lms.service.CourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseEnrollmentMapper courseEnrollmentMapper;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    @Override
    public List<CourseEnrollmentDto> getCourseEnrollmentByStudentId(BigDecimal studentId) {
        Student student = getStudentByIdFromDb(studentId);
        List<CourseEnrollmentDto> response = new ArrayList<>();
        courseEnrollmentRepository.findByStudent(student).forEach(courseEnrollment -> response.add(courseEnrollmentMapper.toDto(courseEnrollment)));
        return response;
    }

    private Student getStudentByIdFromDb(BigDecimal studentId) {
        return studentRepository.getByStudentId(studentId).orElseThrow(() -> new NoSuchRecordException("Student not found"));
    }

    @Override
    public CourseEnrollmentDto enrollStudentToCourse(BigDecimal studentId, BigDecimal courseId) {
        Student student = getStudentByIdFromDb(studentId);
        Course course = getCourseById(courseId);
        Boolean alreadyEnrolled = courseEnrollmentRepository.existsByCourseAndStudent(course, student);

        if (alreadyEnrolled)
            throw new DuplicateRecordException("Student already enrolled for the course");

        CourseEnrollment courseEnrollment = CourseEnrollment.builder()
                .testEnrollments(new HashSet<>())
                .amount(course.getPrice())
                .status(StatusEnum.PENDING)
                .student(student)
                .course(course)
                .build();

        CourseEnrollment savedCourseEnrollment = courseEnrollmentRepository.save(courseEnrollment);

        return courseEnrollmentMapper.toDto(savedCourseEnrollment);
    }

    @Override
    public List<CourseEnrollmentDto> getCourseEnrollments(Pageable pageable) {
        List<CourseEnrollmentDto> response = new ArrayList<>();
        courseEnrollmentRepository.findAll(pageable).forEach(courseEnrollment -> response.add(courseEnrollmentMapper.toDto(courseEnrollment)));
        return response;
    }

    private Course getCourseById(BigDecimal courseId) {
        return courseRepository.getByCourseId(courseId).orElseThrow(() -> new NoSuchRecordException("Course not found"));
    }
}
