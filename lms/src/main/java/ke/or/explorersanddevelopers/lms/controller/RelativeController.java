package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.exception.ErrorDetails;
import ke.or.explorersanddevelopers.lms.model.dto.RelativeDto;
import ke.or.explorersanddevelopers.lms.service.RelativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
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
 * @since Sunday, 09/10/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/relatives")
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
@Tag(name = "Relative Controller", description = "A Controller to manage relative operations")
public class RelativeController {

    private final RelativeService relativeService;

    @PostMapping
    @Operation(summary = "Save a new relative")
    @ApiResponse(responseCode = "201", description = "New Relative Saved Successfully")
    public ResponseEntity<RelativeDto> saveNewRelative(@RequestBody @Validated RelativeDto relativeDto) {
        RelativeDto savedRelative = relativeService.saveNewRelative(relativeDto);
        return ResponseEntity.created(linkTo(methodOn(RelativeController.class).getRelativeById(savedRelative.getRelativeId())).toUri()).body(addHateoasLinks(savedRelative));
    }

    @GetMapping
    @Operation(summary = "Get a list of students")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved a list of students")
    public ResponseEntity<CollectionModel<RelativeDto>> getListOfStudents(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        List<RelativeDto> response = new ArrayList<>();
        relativeService.getListOfRelatives(PageRequest.of(pageNo, pageSize)).forEach(relativeDto -> response.add(addHateoasLinks(relativeDto)));
        CollectionModel<RelativeDto> collectionModel = CollectionModel.of(response);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{relativeId}")
    @Operation(summary = "Get a relative using their record id.")
    @ApiResponse(responseCode = "200", description = "Relative Successfully retrieved")
    public ResponseEntity<RelativeDto> getRelativeById(@PathVariable BigDecimal relativeId) {
        RelativeDto relativeById = relativeService.getRelativeById(relativeId);
        return ResponseEntity.ok(addHateoasLinks(relativeById));
    }

    @GetMapping("/{relativeId}/track-student/{token}")
    @Operation(summary = "Assign a relative a student to track.")
    @ApiResponse(responseCode = "202", description = "A relative is successfully assigned a student to track")
    public ResponseEntity<RelativeDto> trackStudent(@PathVariable BigDecimal relativeId, @PathVariable String token) {
        RelativeDto relativeDto = relativeService.trackStudent(relativeId, token);
        return ResponseEntity.accepted().body(addHateoasLinks(relativeDto));
    }

    @DeleteMapping("/{relativeId}")
    @Operation(summary = "Delete a relative by code")
    @ApiResponse(responseCode = "200", description = "Deleted a relative successfully")
    public ResponseEntity<Boolean> deleteRelativeByCode(@PathVariable BigDecimal relativeId) {
        return ResponseEntity.ok(relativeService.deleteRelativeById(relativeId));
    }

    private RelativeDto addHateoasLinks(RelativeDto savedRelative) {
        savedRelative.add(linkTo(methodOn(RelativeController.class).getRelativeById(savedRelative.getRelativeId())).withSelfRel());
        savedRelative.add(linkTo(methodOn(RelativeController.class).getListOfStudents(0, 10)).withSelfRel());
        savedRelative.add(linkTo(methodOn(RelativeController.class).deleteRelativeByCode(savedRelative.getRelativeId())).withRel("delete"));
        return savedRelative;
    }
}
