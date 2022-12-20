package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.repositories.CourseEnrollmentRepository;
import ke.or.explorersanddevelopers.lms.service.CourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course-enrollments")
public class CourseEnrollmentController {

    private final CourseEnrollmentService courseEnrollmentService;

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Retrieve  course enrolment by student id", description = "An end point to retrieve a course enrollment by student id", tags = "CourseEnrollment")
    @ApiResponse(responseCode = "200", description = "Course retrieved successfully")
    public ResponseEntity<List<CourseEnrollmentDto>> getCourseEnrollmentByStudentId(@PathVariable(value = "studentId") BigDecimal studentId) {
        List<CourseEnrollmentDto> courseEnrollmentByStudentId = courseEnrollmentService.getCourseEnrollmentByStudentId(studentId);
        return ResponseEntity.ok(courseEnrollmentByStudentId);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    @Operation(summary = "Enroll student to course", description = "An end point to enroll a student to course", tags = "CourseEnrollment")
    @ApiResponse(responseCode = "200", description = "Course retrieved successfully")
    public ResponseEntity<CourseEnrollmentDto> enrollStudentToCourse(@PathVariable(value = "studentId") BigDecimal studentId,
                                                                           @PathVariable(value = "courseId") BigDecimal courseId) {
        CourseEnrollmentDto courseEnrollmentDto = courseEnrollmentService.enrollStudentToCourse(studentId,courseId);
        return ResponseEntity.ok(courseEnrollmentDto);
    }
}
