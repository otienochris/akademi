package ke.or.explorersanddevelopers.lms.controller;

//import io.swagger.annotations.ApiResponse;
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
public class RelativeController {

    private final RelativeService relativeService;

    @PostMapping
//    @ApiResponse(code = 201, message = "New Relative Saved Successfully")
    public ResponseEntity<RelativeDto> saveNewRelative(@RequestBody @Validated RelativeDto relativeDto) {
        RelativeDto savedRelative = relativeService.saveNewRelative(relativeDto);
        return ResponseEntity.created(linkTo(methodOn(RelativeController.class).getRelativeById(savedRelative.getRelativeId())).toUri()).body(addHateoasLinks(savedRelative));
    }

    @GetMapping
//    @ApiResponse(code = 200, message = "Successfully retrieved a list of students")
    public ResponseEntity<CollectionModel<RelativeDto>> getListOfStudents(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        List<RelativeDto> response = new ArrayList<>();
        relativeService.getListOfRelatives(PageRequest.of(pageNo, pageSize)).forEach(relativeDto -> response.add(addHateoasLinks(relativeDto)));
        CollectionModel<RelativeDto> collectionModel = CollectionModel.of(response);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{relativeId}")
//    @ApiResponse(code = 200, message = "Relative Successfully retrieved")
    public ResponseEntity<RelativeDto> getRelativeById(@PathVariable BigDecimal relativeId) {
        RelativeDto relativeById = relativeService.getRelativeById(relativeId);
        return ResponseEntity.ok(addHateoasLinks(relativeById));
    }

    @GetMapping("/{relativeId}/track-student/{token}")
//    @ApiResponse(code = 202, message = "A relative is successfully assigned a student to track")
    public ResponseEntity<RelativeDto> trackStudent(@PathVariable BigDecimal relativeId, @PathVariable String token) {
        RelativeDto relativeDto = relativeService.trackStudent(relativeId, token);
        return ResponseEntity.accepted().body(addHateoasLinks(relativeDto));
    }

    @DeleteMapping("/{relativeId}")
//    @ApiResponse(code = 200, message = "Deleted a relative successfully")
    public ResponseEntity<Boolean> deleteRelativeByCode(@PathVariable BigDecimal relativeId) {
        return ResponseEntity.ok(relativeService.deleteRelativeById(relativeId));
    }

    private RelativeDto addHateoasLinks(RelativeDto savedRelative) {
        savedRelative.add(linkTo(methodOn(RelativeController.class).getRelativeById(savedRelative.getRelativeId())).withSelfRel());
        savedRelative.add(linkTo(methodOn(RelativeController.class).getListOfStudents(0, 10)).withSelfRel());
        return savedRelative;
    }
}
