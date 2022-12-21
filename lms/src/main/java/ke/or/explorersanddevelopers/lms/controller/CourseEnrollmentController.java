package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ke.or.explorersanddevelopers.lms.model.dto.CourseEnrollmentDto;
import ke.or.explorersanddevelopers.lms.service.CourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        CourseEnrollmentDto courseEnrollmentDto = courseEnrollmentService.enrollStudentToCourse(studentId, courseId);
        return ResponseEntity.ok(courseEnrollmentDto);
    }

    @PostMapping("/{enrollmentId}/complete-subtopic/{subtopicId}")
    @Operation(summary = "This endpoint allows for the persistence of subtopic completion")
    @ApiResponse(responseCode = "202", description = "The completed topic was persisted successfully")
    public ResponseEntity<CourseEnrollmentDto> completeSubTopic(@PathVariable BigDecimal enrollmentId,
                                                                @PathVariable BigDecimal subtopicId) {
        CourseEnrollmentDto courseEnrollmentDto = courseEnrollmentService.completeSubTopic(enrollmentId, subtopicId);
        return ResponseEntity.accepted().body(courseEnrollmentDto);
    }
}
