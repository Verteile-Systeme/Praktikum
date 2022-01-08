package de.hrw.verteiltesystemepraktikum.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerAcceptanceTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnMethodNotAllowedOnSpecifcEndpoint() throws Exception {
        mockMvc.perform(post("/questions/1"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }
}