package de.hrw.verteiltesystemepraktikum.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)
class QuestionControllerIntegrationTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

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
    void saveQuestion() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"author\": \"%s\",\n" +
                        "    \"email\": \"%s\",\n" +
                        "    \"subject\": \"%s\",\n" +
                        "    \"content\": \"%s\"\n" +
                        "}"
        ,questionInput.getAuthor(), questionInput.getEmail(), questionInput.getSubject(), questionInput.getContent());
        when(questionService
                .saveQuestion(questionInput))
                .thenReturn("Created successfully");
        mockMvc.perform(post("/questions")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(equalTo("Created successfully")));
        verify(questionService).saveQuestion(questionInput);
    }

    @Test
    void getAllQuestions() throws Exception {
        when(questionService
                .getAllQuestions())
                .thenReturn(questionList);
        mockMvc.perform(get("/questions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testQuestion1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(testQuestion1.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(testQuestion1.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].subject").value(testQuestion1.getSubject()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value(testQuestion1.getContent()));
        verify(questionService).getAllQuestions();
    }

    @Test
    void deleteAllQuestions() throws Exception {
        when(questionService
                .deleteAllQuestions())
                .thenReturn("Deleted successfully");
        mockMvc.perform(delete("/questions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Deleted successfully")));
        verify(questionService).deleteAllQuestions();
    }

    @Test
    void getQuestionById() throws Exception {
        when(questionService
                .findQuestionById(1L))
                .thenReturn(Optional.of(testQuestion1));
        mockMvc.perform(get("/questions/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testQuestion1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(testQuestion1.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(testQuestion1.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subject").value(testQuestion1.getSubject()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(testQuestion1.getContent()));
        verify(questionService).findQuestionById(1L);
    }

    @Test
    void updateQuestionById() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"author\": \"%s\",\n" +
                        "    \"email\": \"%s\",\n" +
                        "    \"subject\": \"%s\",\n" +
                        "    \"content\": \"%s\"\n" +
                        "}"
                ,questionInput.getAuthor(), questionInput.getEmail(), questionInput.getSubject(), questionInput.getContent());
        long id = 1;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/questions/" + id)
                        .contentType(APPLICATION_JSON)
                        .content(request);
        when(questionService
                .updateQuestionById(testQuestion1, 1L))
                .thenReturn("Updated successfully");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully")));
        verify(questionService).updateQuestionById(questionInput, 1L);
    }

    @Test
    void updateAllQuestions() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"author\": \"%s\",\n" +
                        "    \"email\": \"%s\",\n" +
                        "    \"subject\": \"%s\",\n" +
                        "    \"content\": \"%s\"\n" +
                        "}"
                ,questionInput.getAuthor(), questionInput.getEmail(), questionInput.getSubject(), questionInput.getContent());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/questions")
                        .contentType(APPLICATION_JSON)
                        .content(request);
        when(questionService
                .updateAllQuestions(questionInput))
                .thenReturn("Updated successfully");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully")));
        verify(questionService).updateAllQuestions(questionInput);
    }

    @Test
    void deleteQuestionById() throws Exception {
        when(questionService
                .deleteQuestionById(1L))
                .thenReturn("Deleted successfully");
        mockMvc.perform(delete("/questions/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Deleted successfully")));
        verify(questionService).deleteQuestionById(1L);
    }
}