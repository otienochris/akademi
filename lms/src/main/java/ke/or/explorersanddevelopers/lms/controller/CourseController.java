package ke.or.explorersanddevelopers.lms.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ke.or.explorersanddevelopers.lms.model.dto.CourseDto;
import ke.or.explorersanddevelopers.lms.model.dto.StudentDto;
import ke.or.explorersanddevelopers.lms.service.CourseService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author oduorfrancis134@gmail.com;
 * @since Monday 10/10/2022
 **/

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 401, message = "UNAUTHORIZED"),
        @ApiResponse(code = 403, message = "FORBIDDEN"),
        @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR"),
})
@Tag(name = "Course", description = "A Controller to manage course operations")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create-a-course")
    @Operation(summary = "Create a course", description = "An end point to create a course", tags = "Course")
    @ApiResponse(code = 201, message = "Course created and saved to database successfully")
    public ResponseEntity<CourseDto> createNewCourse(@RequestBody @Validated CourseDto courseDto){
        CourseDto createdCourse =  courseService.createNewCourse(courseDto);

        return ResponseEntity.created(linkTo(methodOn(CourseController.class).getCourseByCode(createdCourse.getCourseId())).toUri())
                .body(addHateoasLinks(createdCourse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve  a course by course id", description = "An end point to retrieve a course by " +
            "course id",
            tags = "Course")
    @ApiResponse(code = 200, message = "Course retrieved successfully")
    public ResponseEntity<CourseDto> getCourseByCode(@PathVariable(value = "courseId") BigDecimal courseId){
        CourseDto courseByCode = courseService.getCourseByCode(courseId);

        return ResponseEntity.ok(addHateoasLinks(courseByCode));
    }


    @GetMapping("/list-of-courses")
    @Operation(summary = "Get a List of courses", description = "An end point to get a list of courses", tags =
            "Course")
    @ApiResponse(code = 200, message = "List of Courses retrieved successfully")
    private ResponseEntity<CollectionModel<CourseDto>> getListOfCourse(@RequestParam(name = "pageNo", defaultValue =
            "0") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        List<CourseDto> courseDtoList = new ArrayList<>();
        courseService.getListOfCourses(PageRequest.of(pageNo, pageSize)).forEach(courseDto -> {
            courseDtoList.add(addHateoasLinks(courseDto));
        });
        CollectionModel<CourseDto> courseDtoCollectionModel = CollectionModel.of(courseDtoList);
        return ResponseEntity.ok(courseDtoCollectionModel);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Permanently delete a course", description = "An end point to delete a course permanently ",
            tags = "Course")
    @ApiResponse(code = 200, message = "Course Deleted successfully")
    public boolean deleteCourseByCode(@PathVariable(value = "courseId") BigDecimal courseId){
        Boolean courseDto = courseService.deleteCourseByCode(courseId);
        return  courseDto;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update  a course", description = "An end point to update a course   ",
            tags = "Course")
    public ResponseEntity<CourseDto> editACourse(@PathVariable(value = "courseId") BigDecimal courseId,
                                                 @RequestBody CourseDto  courseDto){
        CourseDto editedCourse = courseService.editCourseByCode(courseId, courseDto);

        return ResponseEntity.ok(addHateoasLinks(editedCourse));
    }


            //Add hateoas links
    private CourseDto addHateoasLinks(CourseDto courseByCode) {
        courseByCode.add(linkTo(methodOn(CourseController.class).getCourseByCode(courseByCode.getCourseId())).withSelfRel());
        courseByCode.add(linkTo(methodOn(CourseController.class).getListOfCourse(0, 10)).withRel(IanaLinkRelations.COLLECTION));

        return courseByCode;
    }



}
