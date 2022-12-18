package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.exception.ErrorDetails;
import ke.or.explorersanddevelopers.lms.model.dto.AddressDto;
import ke.or.explorersanddevelopers.lms.model.dto.InstructorDto;
import ke.or.explorersanddevelopers.lms.service.InstructorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Monday, 10/10/2022
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructors")
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
@Tag(name = "Instructor Controller", description = "A Controller to manage instructor operations")
public class InstructorController {
    private final InstructorService instructorService;


    @PostMapping("/signup")
    @Operation(summary = "Save a new instructor")
    @ApiResponse(responseCode = "201", description = "The student was saved successfully")
    public ResponseEntity<InstructorDto> saveNewInstructor(@RequestBody @Validated InstructorDto instructorDto) {
        InstructorDto newInstructor = instructorService.saveNewInstructor(instructorDto);
        return ResponseEntity.created(linkTo(methodOn(InstructorController.class).getInstructorById(newInstructor.getInstructorId())).toUri()).body(addHateoasLinks(newInstructor));
    }

    @PutMapping("/{studentId}")
    @Operation(summary = "Update an instructor")
    @ApiResponse(responseCode = "202", description = "The student was updated successfully")
    public ResponseEntity<InstructorDto> updateInstructor(@PathVariable BigDecimal studentId,
                                                          @RequestBody @Validated InstructorDto instructorDto) {
        InstructorDto newInstructor = instructorService.updateInstructor(studentId, instructorDto);
        return ResponseEntity.accepted().body(addHateoasLinks(newInstructor));
    }

    @GetMapping("/{instructorId}")
    @Operation(summary = "Get an instructor by id")
    @ApiResponse(responseCode = "200", description = "The student was retrieved successfully")
    public ResponseEntity<InstructorDto> getInstructorById(@PathVariable BigDecimal instructorId) {
        InstructorDto newInstructor = instructorService.getInstructorById(instructorId);
        return ResponseEntity.ok(addHateoasLinks(newInstructor));
    }

    @GetMapping
    @Operation(summary = "Get a list of instructors")
    @ApiResponse(responseCode = "200", description = "A list of instructors retrieved successfully")
    public ResponseEntity<CollectionModel<InstructorDto>> getListOfInstructors(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        List<InstructorDto> response = new ArrayList<>();
        instructorService.getListOfInstructors(PageRequest.of(pageNo, pageSize)).forEach(instructorDto -> response.add(addHateoasLinks(instructorDto)));
        CollectionModel<InstructorDto> collectionModel = CollectionModel.of(response);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/username/{email}")
    @Operation(summary = "Get an instructor by email")
    @ApiResponse(responseCode = "200", description = "The student was retrieved successfully")
    public ResponseEntity<InstructorDto> getInstructorByEmail(@PathVariable String email) {
        InstructorDto newInstructor = instructorService.getInstructorByEmail(email);
        return ResponseEntity.ok(addHateoasLinks(newInstructor));
    }

    @DeleteMapping("/{instructorId}")
    @Operation(summary = "Delete an instructor by id")
    @ApiResponse(responseCode = "200", description = "The student was deleted successfully")
    public ResponseEntity<Boolean> deleteInstructorById(@PathVariable BigDecimal instructorId) {
        return ResponseEntity.ok(instructorService.deleteInstructorById(instructorId));
    }
    @PostMapping("/{instructorId}/add-address")
    @Operation(summary = "Add a new address to an instructor")
    @ApiResponse(responseCode = "202", description = "Address was added successfully")
    public ResponseEntity<InstructorDto> addAddress(@PathVariable BigDecimal instructorId, @RequestBody @Validated AddressDto addressDto) {
        InstructorDto instructorDto = instructorService.addAddress(instructorId, addressDto);
        return ResponseEntity.accepted().body(addHateoasLinks(instructorDto));
    }

    private InstructorDto addHateoasLinks(InstructorDto instructorDto) {
        instructorDto.add(linkTo(methodOn(InstructorController.class).getInstructorById(instructorDto.getInstructorId())).withSelfRel());
        instructorDto.add(linkTo(methodOn(InstructorController.class).deleteInstructorById(instructorDto.getInstructorId())).withRel("delete"));
        instructorDto.add(linkTo(methodOn(InstructorController.class).getListOfInstructors(0, 10)).withRel(IanaLinkRelations.COLLECTION));
        return instructorDto;
    }
}
