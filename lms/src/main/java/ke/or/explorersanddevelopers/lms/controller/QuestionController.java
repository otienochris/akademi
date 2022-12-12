package ke.or.explorersanddevelopers.lms.controller;

import ke.or.explorersanddevelopers.lms.model.dto.QuestionDto;
import ke.or.explorersanddevelopers.lms.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 11/11/2022
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionDto> saveQuestion(@RequestBody QuestionDto questionDto){
        QuestionDto question = questionService.saveQuestion(questionDto);
        return ResponseEntity.created(linkTo(methodOn(QuestionController.class).getQuestionById(question.getQuestionId())).toUri()).body(addHateoasLinks(question));
    }



    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable BigDecimal questionId) {
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

    private QuestionDto addHateoasLinks(QuestionDto question) {
        question.add(linkTo(methodOn(QuestionController.class).getQuestionById(question.getQuestionId())).withSelfRel());
        question.add(linkTo(methodOn(QuestionController.class).getQuestionById(question.getQuestionId())).withSelfRel());
        return question;
    }
}
