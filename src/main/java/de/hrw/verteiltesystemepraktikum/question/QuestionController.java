package de.hrw.verteiltesystemepraktikum.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<?> saveQuestion(@Valid @RequestBody Question question) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(questionService.saveQuestion(question));
    }

    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(questionService.getAllQuestions());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllQuestions() {
        long entites = questionService.deleteAllQuestions();
        String response = String.format("%d Entites deleted.", entites);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(questionService.findQuestionById(id));
        } catch (QuestionNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestionById(@PathVariable Long id, @Valid @RequestBody Question updatedQuestion) {
        Optional<Question> optionalQuestion = questionService.findQuestionById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            if (question.equals(updatedQuestion)) {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
        try {
            return new ResponseEntity<>(questionService.updateQuestionById(updatedQuestion, id), HttpStatus.OK);
        } catch (QuestionNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAllQuestions(@Valid @RequestBody Question updatedQuestion) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(questionService.updateAllQuestions(updatedQuestion));
        } catch (QuestionNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable Long id) {
        try {
            questionService.deleteQuestionById(id);
            return new ResponseEntity<>("Deleted successfully.",HttpStatus.OK);
        } catch (QuestionNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
