package de.hrw.verteiltesystemepraktikum.question;

import de.hrw.verteiltesystemepraktikum.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String saveQuestion(@Valid @RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @DeleteMapping
    public String deleteAllQuestions() {
        return questionService.deleteAllQuestions();
    }

    @GetMapping("/{id}")
    public Optional<Question> getQuestionById(@PathVariable Long id) {
        return questionService.findQuestionById(id);
    }

    @PutMapping("/{id}")
    public String updateQuestionById(@PathVariable Long id, @Valid @RequestBody Question updatedQuestion) {
        return questionService.updateQuestionById(updatedQuestion, id);
    }

    @PutMapping
    public String updateAllQuestions(@Valid @RequestBody Question updatedQuestion) {
        return questionService.updateAllQuestions(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestionById(@PathVariable Long id) {
        return questionService.deleteQuestionById(id);
    }

    @ExceptionHandler({QuestionNotFoundException.class})
    public ResponseEntity<String> handleException(QuestionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
