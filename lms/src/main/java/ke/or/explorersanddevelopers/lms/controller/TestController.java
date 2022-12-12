package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.exception.ErrorDetails;
import ke.or.explorersanddevelopers.lms.model.dto.TestDto;
import ke.or.explorersanddevelopers.lms.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Wednesday 26/10/2022
 **/

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/tests")
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
@Tag(name = "Test", description = "A controller to manage all  tests ")
public class TestController {
    private final TestService testService;

    @PostMapping
    @Operation(summary = "Create  a new test", description = "An endpoint to create  a new test" + "and add  associte" +
            " it to a topic", tags = "Test")
    @ApiResponse(responseCode = "201", description = "Test successfully created, mapped to the topic and saved " +
            "to the database")
    public ResponseEntity<TestDto> createNewTest(@RequestParam(name = "topicId") BigDecimal topicId,
                                                 @RequestBody @Validated TestDto testDto){
        
        TestDto createdTest = testService.createNewTest(topicId, testDto);

        return ResponseEntity.created(linkTo(methodOn(TestController.class).getTestById(createdTest.getTestId())).toUri())
                .body(addHateoasLinks(createdTest));
    }

    @GetMapping("/{testId}")
    @Operation(summary = "Retrieve a test by id", description = "An endpoint to retrieve a test by id")
    @ApiResponse(responseCode = "200", description = "Test successfully  retrieved")
    public ResponseEntity<TestDto> getTestById(@PathVariable(name = "testId") BigDecimal testId) {
        
        TestDto testById = testService.getTestById(testId);

        return ResponseEntity.ok(addHateoasLinks(testById));
    }

    @PutMapping("/{testId}")
    @Operation(summary = "Edit a test by id", description = "An endpoint to edit a test by id")
    @ApiResponse(responseCode = "200", description = "Test successfully  updated")
    public ResponseEntity<TestDto> edtTestById(@PathVariable(name = "testId") BigDecimal testId,
                                               @RequestBody @Validated TestDto testDto){

        TestDto updatedTest = testService.edtTestById(testId, testDto);

        return ResponseEntity.ok(addHateoasLinks(updatedTest));
    }

    @GetMapping
    @Operation(summary = "Get a list of tests", description = "An endpoint to retrieve a list of tests")
    @ApiResponse(responseCode = "200", description = "List of tests  successfully  retrieved")
    public ResponseEntity<CollectionModel<TestDto>> getListOfTests(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        List<TestDto> testDtoList = new ArrayList<>();
        testService.getListOfTests((Pageable) PageRequest.of(pageNo, pageSize))
                .forEach(testDto -> testDtoList.add(addHateoasLinks(testDto)));

        CollectionModel<TestDto> testDtoCollectionModel = CollectionModel.of(testDtoList);

        return ResponseEntity.ok(testDtoCollectionModel);
    }

    @DeleteMapping("/{testId}")
    @Operation(summary = "Delete a test", description = "An endpoint to delete a test using its id")
    @ApiResponse(responseCode = "200", description = "Test deleted successfully")
    public ResponseEntity<Map<String, Boolean>>  deleteTestById(@PathVariable(name = "testId") BigDecimal testId){

        testService.deleteTestById(testId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Test successfully deleted", Boolean.TRUE);

        return  ResponseEntity.ok(response);
    }

    private TestDto addHateoasLinks(TestDto testById){
        testById.add(linkTo(methodOn(TestController.class).getTestById(testById.getTestId())).withSelfRel());
        testById.add(linkTo(methodOn(TestController.class).deleteTestById(testById.getTestId())).withRel("delete"));
        testById.add(linkTo(methodOn(TestController.class).getListOfTests(0, 10)).withRel(IanaLinkRelations.COLLECTION));

        return testById;
    }

}
