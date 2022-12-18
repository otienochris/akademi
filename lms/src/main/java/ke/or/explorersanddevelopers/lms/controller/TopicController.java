package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.exception.ErrorDetails;
import ke.or.explorersanddevelopers.lms.model.dto.TopicDto;
import ke.or.explorersanddevelopers.lms.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
 * @since Saturday 15/10/2022
 **/
@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
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
@Tag(name = "Topic", description = "A controller to manage all topics")
public class TopicController {

    private final TopicService topicService;


    //hateoas links
    public static TopicDto addHateoasLinksToTopicDto(TopicDto topicById) {
        topicById.add(linkTo(methodOn(TopicController.class).getTopicById(topicById.getTopicId())).withSelfRel());
        topicById.add(linkTo(methodOn(TopicController.class).deleteTopicById(topicById.getTopicId())).withRel("delete"));
        topicById.add(linkTo(methodOn(TopicController.class).getListOfTopics(0, 10)).withRel(IanaLinkRelations.COLLECTION));

        return topicById;

    }

    @PostMapping
    @Operation(summary = "Create new topic", description = "This is an endpoint to create  add a new topic to a given" +
            " course", tags = "Topic")
    @ApiResponse(responseCode = "201", description = "Topic successfully created, mapped to the course, and saved to" +
            " the database")
    public ResponseEntity<TopicDto> createNewTopic(@RequestParam(name = "courseId") BigDecimal courseId,
                                                   @RequestBody @Validated TopicDto topicDto) {

        TopicDto createdTopic = topicService.createNewTopic(courseId, topicDto);

        return ResponseEntity.created(linkTo(methodOn(TopicController.class).getTopicById(createdTopic.getTopicId())).toUri())
                .body(addHateoasLinksToTopicDto(createdTopic));

    }

    @GetMapping("/{topicId}")
    @Operation(summary = "Retrieve a topic by id", description = "An endpoint to retrieve a topic by its id", tags = "Topic")
    @ApiResponse(responseCode = "200", description = "Topic successfully retrieved")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable(name = "topicId") BigDecimal topicId){

        TopicDto topicById = topicService.getTopicById(topicId);

        return ResponseEntity.ok(addHateoasLinksToTopicDto(topicById));

    }

    @DeleteMapping("/{topicId}")
    @Operation(summary = "Delete a topic by id", description = "This is an endpoint to delete  topic using its id", tags = "Topic")
    @ApiResponse(responseCode = "200", description = "Topic successfully deleted")
    public ResponseEntity<Map<String, Boolean>> deleteTopicById(@PathVariable(value = "topicId") BigDecimal topicId){
        topicService.deleteTopicById(topicId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted topic with id: " + topicId, Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(summary = "Delete all topics", description = "THis is an endpoint to delete all topics from the " +
            "database", tags = "Topic")
    @ApiResponse(responseCode = "200", description = "All topics in the database deleted")
    public ResponseEntity<Map<String, Boolean>> deleteTopicById() {
        topicService.deleteAllTopics();

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted all topics", Boolean.TRUE);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    @Operation(summary = "Get a list of topics", description = "This is n endpoint to retrieve a list of all the " +
            "topics", tags = "Topic")
    @ApiResponse(responseCode = "200", description = "A list of topics successfully retrieved")
    public ResponseEntity<CollectionModel<TopicDto>> getListOfTopics(@RequestParam(name = "pageNo",
            defaultValue = "0") Integer pageNo,
                                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        List<TopicDto> listOfTopics = new ArrayList<>();
        topicService.getListOfTopics(PageRequest.of(pageNo, pageSize)).
                forEach(topicDto -> listOfTopics.add(addHateoasLinksToTopicDto(topicDto)));

        CollectionModel<TopicDto> topicDtoCollectionModel = CollectionModel.of(listOfTopics);

        return ResponseEntity.ok(topicDtoCollectionModel);
    }

    @PutMapping("/{topicId}")
    @Operation(summary = "Edit a topic", description = "This is an endpoint to edit a given topic using its a id")
    @ApiResponse(responseCode = "200", description = "Sub Topic successfully updated")
    public ResponseEntity<TopicDto> editTopicById(@PathVariable(name = "topicId") BigDecimal topicId,
                                                  @RequestBody TopicDto topicDto) {

        TopicDto editedTopic = topicService.editTopicById(topicId, topicDto);

        return ResponseEntity.ok(addHateoasLinksToTopicDto(editedTopic));


    }


}
