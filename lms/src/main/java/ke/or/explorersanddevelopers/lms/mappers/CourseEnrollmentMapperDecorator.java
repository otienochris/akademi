package ke.or.explorersanddevelopers.lms.mappers;

import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.model.entity.Course;
import ke.or.explorersanddevelopers.lms.model.entity.CourseEnrollment;
import ke.or.explorersanddevelopers.lms.model.entity.Student;
import ke.or.explorersanddevelopers.lms.model.entity.TestEnrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
public class CourseEnrollmentMapperDecorator implements CourseEnrollmentMapper {

    @Autowired
    @Qualifier("delegate")
    private CourseEnrollmentMapper courseEnrollmentMapper;

    @Autowired
    @Qualifier("delegate")
    private StudentMapper studentMapper;

    @Autowired
    @Qualifier("delegate")
    private CourseMapper courseMapper;

    @Autowired
    @Qualifier("delegate")
    private TestEnrollmentMapper testEnrollmentMapper;


    @Override
    public CourseEnrollmentDto toDto(CourseEnrollment courseEnrollment) {
        CourseEnrollmentDto mappedCourseEnrollmentDto = courseEnrollmentMapper.toDto(courseEnrollment);
        // set student
        Student student = courseEnrollment.getStudent();
        if (student != null) {
            StudentDto studentDto = studentMapper.toDto(student);
            mappedCourseEnrollmentDto.setStudent(studentDto);
        }

        // set course
        Course course = courseEnrollment.getCourse();
        if (course != null) {
            CourseDto courseDto = courseMapper.toDto(course);
            mappedCourseEnrollmentDto.setCourse(courseDto);
        }

        // set test enrollments
        mappedCourseEnrollmentDto.setTestEnrollments(new ArrayList<>());
        List<TestEnrollment> testEnrollments = courseEnrollment.getTestEnrollments();
        if (testEnrollments != null && testEnrollments.size() > 0) {
            testEnrollments.forEach(testEnrollment -> mappedCourseEnrollmentDto.getTestEnrollments().add(testEnrollmentMapper.toDto(testEnrollment)));
        }
        return mappedCourseEnrollmentDto;
    }
}
