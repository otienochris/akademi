package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.CertificateDto;
import ke.or.explorersanddevelopers.lms.model.dto.ReviewDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
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
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 401, message = "UNAUTHORIZED"),
        @ApiResponse(code = 403, message = "FORBIDDEN"),
        @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
})
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ApiResponse(code = 201, message = "Student Created and Saved Successfully.")
    public ResponseEntity<StudentDto> saveNewStudent(@RequestBody @Validated StudentDto studentDto){
        System.out.println(studentDto);
        StudentDto savedStudentDto = studentService.saveNewStudent(studentDto);
        return ResponseEntity.created(linkTo(methodOn(StudentController.class).getStudentById(savedStudentDto.getStudentId())).toUri()).body(addHateoasLinks(savedStudentDto));
    }

    @GetMapping("/{studentId}")
    @ApiResponse(code = 200, message = "Student Retrieved Successfully")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable BigDecimal studentId) {
        StudentDto studentByCode = studentService.getStudentByCode(studentId);
        return ResponseEntity.ok(addHateoasLinks(studentByCode));
    }

    @GetMapping
    @ApiResponse(code = 200, message = "Students' list Retrieved Successfully")
    public ResponseEntity<CollectionModel<StudentDto>> getListOfStudents(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        List<StudentDto> response = new ArrayList<>();
        studentService.getListOfStudents(PageRequest.of(pageNo, pageSize)).forEach(studentDto -> response.add(addHateoasLinks(studentDto)));
        CollectionModel<StudentDto> collectionModel = CollectionModel.of(response);
        return ResponseEntity.ok(collectionModel);
    }

    @DeleteMapping("/{studentId}")
    @ApiResponse(code = 200, message = "The student was deleted successfully")
    public ResponseEntity<Boolean> deleteStudentById(@PathVariable BigDecimal studentId) {
        return ResponseEntity.ok(studentService.deleteStudentByCode(studentId));
    }

    @PostMapping("/{studentId}/add-review/{targetId}")
    @ApiResponse(code = 200, message = "The student submitted the review successfully")
    public ResponseEntity<Boolean> submitReview(@PathVariable BigDecimal studentId, @PathVariable BigDecimal targetId, @RequestBody @Validated ReviewDto reviewDto) {
        return ResponseEntity.ok(studentService.submitReview(studentId, targetId, reviewDto));
    }

    @PostMapping("/{studentId}/add-address")
    @ApiResponse(code = 200, message = "The student add an address successfully")
    public ResponseEntity<AddressDto> addAddress(@PathVariable BigDecimal studentId, @RequestBody @Validated AddressDto addressDto) {
        return ResponseEntity.ok(studentService.addAddress(studentId, addressDto));
    }

    @GetMapping("/{studentId}/certificates")
    @ApiResponse(code = 200, message = "The certificates were retrieved successfully")
    public ResponseEntity<List<CertificateDto>> retrieveCertificates(@PathVariable BigDecimal studentId) {
        return ResponseEntity.ok(studentService.retrieveCertificates(studentId));
    }

    @GetMapping("/{studentId}/generate-token")
    @ApiResponse(code = 200, message = "The token was generated successfully")
    public ResponseEntity<Map<String, UUID>> generateToken(@PathVariable BigDecimal studentId) {
        UUID uuid = studentService.generateToken(studentId);
        Map<String, UUID> response = Map.of("Token", uuid);
        return ResponseEntity.ok(response);
    }

    private StudentDto addHateoasLinks(StudentDto savedStudentDto) {
        savedStudentDto.add(linkTo(methodOn(StudentController.class).getStudentById(savedStudentDto.getStudentId())).withSelfRel());
        savedStudentDto.add(linkTo(methodOn(StudentController.class).getListOfStudents(0, 10)).withRel(IanaLinkRelations.COLLECTION));
        return savedStudentDto;
    }
}
