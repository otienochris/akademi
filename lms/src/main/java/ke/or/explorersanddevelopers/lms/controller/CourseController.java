package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.exception.ErrorDetails;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since Monday 10/10/2022
 **/

@RestController
@RequestMapping("/api/v1/courses")
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
@Tag(name = "Course", description = "A Controller to manage course operations")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Operation(summary = "Create a course", description = "An end point to create a course", tags = "Course")
    @ApiResponse(responseCode = "201", description = "Course created and saved to database successfully")
    public ResponseEntity<CourseDto> createNewCourse(@RequestParam(name = "instructorId") BigDecimal instructorId, @RequestBody @Validated CourseDto courseDto) {
        CourseDto createdCourse = courseService.createNewCourse(instructorId, courseDto);
        return ResponseEntity.created(linkTo(methodOn(CourseController.class).getCourseByCode(createdCourse.getCourseId())).toUri())
                .body(addHateoasLinks(createdCourse));
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Retrieve  a course by course id", description = "An end point to retrieve a course by course id", tags = "Course")
    @ApiResponse(responseCode = "200", description = "Course retrieved successfully")
    public ResponseEntity<CourseDto> getCourseByCode(@PathVariable(value = "courseId") BigDecimal courseId) {
        CourseDto courseByCode = courseService.getCourseByCode(courseId);
        return ResponseEntity.ok(addHateoasLinks(courseByCode));
    }

    @GetMapping
    @Operation(summary = "Get a List of courses", description = "An end point to get a list of courses", tags =
            "Course")
    @ApiResponse(responseCode = "200", description = "List of Courses retrieved successfully")
    public ResponseEntity<CollectionModel<CourseDto>> getListOfCourses(@RequestParam(name = "pageNo",
            defaultValue = "0") Integer pageNo,
                                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        List<CourseDto> listOfCourses = new  ArrayList<>();
        courseService.getListOfCourses(PageRequest.of(pageNo, pageSize))
                        .forEach(courseDto -> listOfCourses.add(addHateoasLinks(courseDto)));
        System.out.println("Found the following courses: " + listOfCourses);

        CollectionModel<CourseDto> courseDtoCollectionModel = CollectionModel.of(listOfCourses);
        return ResponseEntity.ok(courseDtoCollectionModel);
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "Permanently delete a course", description = "An end point to delete a course permanently ",
            tags = "Course")
    @ApiResponse(responseCode = "200", description = "Course Deleted successfully")
    public Map<String, Boolean> deleteCourseByCode(@PathVariable(value = "courseId") BigDecimal courseId) {
        courseService.deleteCourseByCode(courseId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Course Deleted", Boolean.TRUE);

        return response;
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Update  a course", description = "An end point to update a course.",
            tags = "Course")
    @ApiResponse(responseCode = "200", description = "Course Edited successfully")
    public ResponseEntity<CourseDto> editCourse(@PathVariable(value = "courseId") BigDecimal courseId,
                                                @RequestBody CourseDto courseDto) {
        CourseDto editedCourse = courseService.editCourseByCode(courseId, courseDto);
        return ResponseEntity.ok(addHateoasLinks(editedCourse));
    }


    //Add hateoas links
    private CourseDto addHateoasLinks(CourseDto courseByCode) {
        courseByCode.add(linkTo(methodOn(CourseController.class).getCourseByCode(courseByCode.getCourseId())).withSelfRel());
        courseByCode.add(linkTo(methodOn(CourseController.class).deleteCourseByCode(courseByCode.getCourseId())).withRel("delete"));
        courseByCode.add(linkTo(methodOn(CourseController.class).getListOfCourses(0,10)).withRel(IanaLinkRelations.COLLECTION));

        return courseByCode;
    }



}
