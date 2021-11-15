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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(AppUserController.class)
@AutoConfigureMockMvc
public class AppUserControllerIntegrationTest {

    @MockBean
    private AppUserService appUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestToUsersAndEmptyFields_thenCorrectResponse() throws Exception {
        String user = "{\"firstname\": \"\", \"lastname\": \"\", \"email\": \"\", \"password\": \"\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Is.is("Empty firstname is not allowed.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Is.is("Empty lastname is not allowed.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("Empty email is not allowed.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("Empty password is not allowed.")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToUsersAndFieldsAreNull_thenCorrectResponse() throws Exception {
        String user = "{}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Is.is("A firstname is required.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Is.is("A lastname is required.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("An email is required.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("A password is required.")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String password = faker.crypto().md5();
        String email = String.format("%s.%s@%s.com", firstname, lastname, lastname);
        String user = String.format("{\"firstname\": \"%s\", \"lastname\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }", firstname, lastname, email, password);
        AppUser testUser = new AppUser(firstname, lastname, email, password);
        when(appUserService.saveUser(any(AppUser.class))).thenReturn(testUser);
        this.mockMvc.perform(post("/users").content(user).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Is.is(firstname)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Is.is(lastname)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is(password)))
                .andExpect(content().string(containsString(firstname)));
    }

}
