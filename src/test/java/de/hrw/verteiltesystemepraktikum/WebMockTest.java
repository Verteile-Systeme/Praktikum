package de.hrw.verteiltesystemepraktikum;

import com.github.javafaker.Faker;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserController;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppUserController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = faker.crypto().md5();
        String email = String.format("%s.%s@%s.com", firstname, lastName, lastName);
        String user = String.format("{\"firstname\": \"%s\", \"lastname\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }", firstname, lastName, email, password);
        this.mockMvc.perform(post("/users").content(user).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andExpect(content().string(containsString(firstname)));
    }

}
