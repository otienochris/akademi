package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.exception.ErrorDetails;
import ke.or.explorersanddevelopers.lms.model.dto.*;
import ke.or.explorersanddevelopers.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
        @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
        @ApiResponse(responseCode = "404", description = "RESOURCE NOT FOUND", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
        }),
})
@Tag(name = "Student Controller", description = "A Controller to manage student operations")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Save a new student")
    @ApiResponse(responseCode = "201", description = "Student Created and Saved Successfully.")
    public ResponseEntity<StudentDto> saveNewStudent(@RequestBody @Validated StudentDto studentDto){
        StudentDto savedStudentDto = studentService.saveNewStudent(studentDto);
        return ResponseEntity.created(linkTo(methodOn(StudentController.class).getStudentById(savedStudentDto.getStudentId())).toUri()).body(addHateoasLinks(savedStudentDto));
    }

    @GetMapping("/{studentId}")
    @Operation(summary = "Get a student by their record id.")
    @ApiResponse(responseCode = "200", description = "Student Retrieved Successfully")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable BigDecimal studentId) {
        StudentDto studentByCode = studentService.getStudentByCode(studentId);
        return ResponseEntity.ok(addHateoasLinks(studentByCode));
    }

    @GetMapping
    @Operation(summary = "Get a list of students")
    @ApiResponse(responseCode = "200", description = "Students' list Retrieved Successfully")
    public ResponseEntity<CollectionModel<StudentDto>> getListOfStudents(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        List<StudentDto> response = new ArrayList<>();
        studentService.getListOfStudents(PageRequest.of(pageNo, pageSize)).forEach(studentDto -> response.add(addHateoasLinks(studentDto)));
        CollectionModel<StudentDto> collectionModel = CollectionModel.of(response);
        return ResponseEntity.ok(collectionModel);
    }

    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete a student using their record id")
    @ApiResponse(responseCode = "200", description = "The student was deleted successfully")
    public ResponseEntity<Boolean> deleteStudentById(@PathVariable BigDecimal studentId) {
        return ResponseEntity.ok(studentService.deleteStudentByCode(studentId));
    }

    @PostMapping("/{studentId}/add-review/{targetId}")
    @Operation(summary = "Submit a review")
    @ApiResponse(responseCode = "202", description = "The student submitted the review successfully")
    public ResponseEntity<Boolean> submitReview(@PathVariable BigDecimal studentId, @PathVariable BigDecimal targetId, @RequestBody @Validated ReviewDto reviewDto) {
        return ResponseEntity.ok(studentService.submitReview(studentId, targetId, reviewDto));
    }

    @PostMapping("/{studentId}/add-address")
    @Operation(summary = "Add a new address to a student")
    @ApiResponse(responseCode = "202", description = "The student add an address successfully")
    public ResponseEntity<AddressDto> addAddress(@PathVariable BigDecimal studentId, @RequestBody @Validated AddressDto addressDto) {
        return ResponseEntity.ok(studentService.addAddress(studentId, addressDto));
    }

    @GetMapping("/{studentId}/certificates")
    @Operation(summary = "Retrieves a list of certificates owned by a student.")
    @ApiResponse(responseCode = "200", description = "The certificates were retrieved successfully")
    public ResponseEntity<List<CertificateDto>> retrieveCertificates(@PathVariable BigDecimal studentId) {
        return ResponseEntity.ok(studentService.retrieveCertificates(studentId));
    }

    @GetMapping("/{studentId}/generate-token")
    @Operation(summary = "Generate a student token used for different operations like tracking the student.")
    @ApiResponse(responseCode = "200", description = "The token was generated successfully")
    public ResponseEntity<Map<String, UUID>> generateToken(@PathVariable BigDecimal studentId) {
        UUID uuid = studentService.generateToken(studentId);
        Map<String, UUID> response = Map.of("Token", uuid);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{studentId}/complete-topic")
    @Operation(summary = "This endpoint allows a student to submit completed topic for persistence")
    @ApiResponse(responseCode = "202", description = "The completed topic was persisted successfully")
    public ResponseEntity<CourseEnrollmentDto> completeTopic(@PathVariable BigDecimal studentId,
                                                             @RequestParam(name = "courseId") BigDecimal courseId,
                                                             @RequestParam(name = "topicId") BigDecimal topicId) {
        CourseEnrollmentDto courseEnrollmentDto = studentService.completeTopic(studentId, courseId, topicId);
        return ResponseEntity.accepted().body(courseEnrollmentDto);
    }

    private StudentDto addHateoasLinks(StudentDto savedStudentDto) {
        savedStudentDto.add(linkTo(methodOn(StudentController.class).getStudentById(savedStudentDto.getStudentId())).withSelfRel());
        savedStudentDto.add(linkTo(methodOn(StudentController.class).getListOfStudents(0, 10)).withRel(IanaLinkRelations.COLLECTION));
        savedStudentDto.add(linkTo(methodOn(StudentController.class).deleteStudentById(savedStudentDto.getStudentId())).withRel("delete"));
        return savedStudentDto;
    }
}
