package de.hrw.verteiltesystemepraktikum.question;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class QuestionControllerUnitTest {

    QuestionService questionService = Mockito.mock(QuestionService.class);
    QuestionController questionController = new QuestionController(questionService);

    Question questionInput = new Question(
            "testAuthor",
            "testmail",
            "testsubject",
            "testcontent"
    );


    Question testQuestion1 = new Question(
            1L,
            "testAuthor",
            "testmail",
            "testsubject",
            "testcontent"
    );

    Question testQuestion2 = new Question(
            2L,
            "testAuthor",
            "testmail",
            "testsubject",
            "testcontent"
    );

    List<Question> questionList = Arrays.asList(testQuestion1, testQuestion2);

    @Test
    void saveQuestion() {
        when(questionService.saveQuestion(questionInput)).thenReturn("Created successfully");
        assertEquals("Created successfully", questionController.saveQuestion(questionInput));
    }

    @Test
    void getAllQuestions() {
        when(questionService.getAllQuestions()).thenReturn(questionList);
        assertEquals(questionList, questionController.getAllQuestions());
    }

    @Test
    void deleteAllQuestions() {
        when(questionService.deleteAllQuestions()).thenReturn("Deleted successfully");
        assertEquals("Deleted successfully", questionController.deleteAllQuestions());
    }

    @Test
    void getQuestionById() {
        when(questionService.findQuestionById(1L)).thenReturn(Optional.of(testQuestion1));
        assertEquals(Optional.of(testQuestion1), questionController.getQuestionById(1L));
    }

    @Test
    void updateQuestionById() {
        when(questionService.updateQuestionById(testQuestion1, 1L)).thenReturn("Updated successfully");
        assertEquals("Updated successfully", questionController.updateQuestionById(1L, testQuestion1));
    }

    @Test
    void updateAllQuestions() {
        when(questionService.updateAllQuestions(testQuestion1)).thenReturn("Updated successfully");
        assertEquals("Updated successfully", questionController.updateAllQuestions(testQuestion1));
    }

    @Test
    void deleteQuestionById() {
        when(questionService.deleteQuestionById(1L)).thenReturn("Deleted successfully");
        assertEquals("Deleted successfully", questionController.deleteQuestionById(1L));
    }
}