package de.hrw.verteiltesystemepraktikum;

import com.github.javafaker.Faker;
import de.hrw.verteiltesystemepraktikum.appuser.AppUser;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserController;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserRepository;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserService;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;


@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class AppUserControllerIntegrationTest {

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private AppUserRepository appUserRepository;

    @Autowired
    AppUserController appUserController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestToUsersAndInvalidUser_thenCorrectResponse() throws Exception {
        String user = "{\"firstname\": \"\", \"lastname\": \"\", \"email\": \"\", \"password\": \"\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Is.is("must not be empty")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Is.is("must not be empty")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("must not be empty")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("must not be empty")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = faker.crypto().md5();
        String email = String.format("%s.%s@%s.com", firstname, lastName, lastName);
        String user = String.format("{\"firstname\": \"%s\", \"lastname\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }", firstname, lastName, email, password);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

}
