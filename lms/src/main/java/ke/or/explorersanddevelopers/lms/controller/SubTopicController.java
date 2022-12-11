package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.exception.ErrorDetails;
import ke.or.explorersanddevelopers.lms.model.dto.SubTopicDto;
import ke.or.explorersanddevelopers.lms.service.SubTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/subtopics")
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
@Tag(name = "SubTopic", description = "A controller to manage all sub topics")
public class SubTopicController {

    private final SubTopicService subTopicService;


    @PostMapping
    @Operation(summary = "Create  a new sub topic", description = "An endpoint to create a new sub topic and add it " +
            "to a given topic",    tags = "SubTopic")
    @ApiResponse(responseCode = "201", description = "Sub topic successfully created, mapped to the topic and saved " +
            "to the database")
    public ResponseEntity<SubTopicDto> createNewSubTopic(@RequestParam(name = "topicId") BigDecimal topicId,
                                                         @RequestBody @Validated SubTopicDto subTopicDto){

        SubTopicDto createdSubTopic = subTopicService.createNewSubTopic(topicId, subTopicDto);

        return ResponseEntity.created(linkTo(methodOn(SubTopicController.class).getSubTopicById(createdSubTopic.getSubTopicId())).toUri())
                .body(addHateoasLinks(createdSubTopic));

    }

    @GetMapping("/{topicId}")
    @Operation(summary = "Retrieve a sub topic by id", description = "An endpoint to retrieve a sub topic by its id",
            tags = "SubTopic")
    @ApiResponse(responseCode = "200", description = "Sub Topic successfully retrieved")
    public ResponseEntity<SubTopicDto> getSubTopicById(@PathVariable(name = "topicId") BigDecimal subTopicId) {

        SubTopicDto getSubTopicsId = subTopicService.getSubTopicById(subTopicId);

        return ResponseEntity.ok(addHateoasLinks(getSubTopicsId));

    }

     @DeleteMapping("/{subTopicId}")
     @Operation(summary = "Delete a sub topic by id", description = "An endpoint to delete a sub topic by its id",
             tags = "SubTopic")
     @ApiResponse(responseCode = "200", description = "Sub Topic successfully deleted")
    public ResponseEntity<Map<String, Boolean>> deleteSubtopicById(@PathVariable(name = "subTopicId") BigDecimal subTopicId){
        subTopicService.deleteSubtopicById(subTopicId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Sub topic delete", Boolean.TRUE);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    @Operation(summary = "Get a list of all sub topics ", description = "An endpoint to get a " +
            "list of sub topics", tags = "SubTopic")
    @ApiResponse(responseCode = "200", description = "A list of Sub Topics successfully retrieved ")
    public ResponseEntity<CollectionModel<SubTopicDto>> getListOfSubTopics(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        List<SubTopicDto> subTopicDtoList = new ArrayList<>();
        subTopicService.getListOfSubTopics(PageRequest.of(pageNo, pageSize))
                .forEach(subTopicDto -> subTopicDtoList.add(addHateoasLinks(subTopicDto)));

        CollectionModel<SubTopicDto> subTopicDtoCollectionModel = CollectionModel.of(subTopicDtoList);

        return ResponseEntity.ok(subTopicDtoCollectionModel);

    }

    @PutMapping("/{subTopicId}")
    @Operation(summary = "Update a sub topic using its id ", description = "An endpoint to update a " +
            "sub topics", tags = "SubTopic")
    @ApiResponse(responseCode = "200", description = "Sub Topic successfully updated ")
    public ResponseEntity<SubTopicDto> editSubTopicById(@PathVariable(name = "subTopicId") BigDecimal subTopicId,
                                                        @RequestBody @Validated SubTopicDto subTopicDto){

        SubTopicDto updatedSubTopic = subTopicService.editSubTopicById(subTopicId, subTopicDto);

        return ResponseEntity.ok(addHateoasLinks(updatedSubTopic));

    }


    private SubTopicDto  addHateoasLinks(SubTopicDto subTopicById){
        subTopicById.add(linkTo(methodOn(SubTopicController.class).getSubTopicById(subTopicById.getSubTopicId())).withSelfRel());
        subTopicById.add(linkTo(methodOn(SubTopicController.class).deleteSubtopicById(subTopicById.getSubTopicId())).withRel("delete"));
        subTopicById.add(linkTo(methodOn(SubTopicController.class).getListOfSubTopics(0, 10)).withRel(IanaLinkRelations.COLLECTION));

        return subTopicById;
    }



}
