package de.hrw.verteiltesystemepraktikum.appuser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppUserController.class)
class AppUserControllerIntegrationTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    AppUser testAppUser = new AppUser(
            1L,
            "testfirstname",
            "testlastname",
            "test@email.de",
            "testaddress",
            "testpassword"
    );

    AppUser testAppUser2 = new AppUser(
            2L,
            "testfirstname",
            "testlastname",
            "test@email.de",
            "testaddress",
            "testpassword"
    );

    AppUser input = new AppUser(
            "testfirstname",
            "testlastname",
            "test@email.de",
            "testaddress",
            "testpassword"
    );

    List<AppUser> listAppUser = Arrays.asList(testAppUser, testAppUser2);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    @Test
    void deleteUserById() throws Exception {
        doNothing().when(appUserService).deleteUserById(1L);
        mockMvc.perform(delete("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Deleted successfully")));
        verify(appUserService).deleteUserById(1L);
    }

    @Test
    void deleteAllUsers() throws Exception {
        when(appUserService.deleteAllUsers()).thenReturn(5L);
        mockMvc.perform(delete("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("5 Entities deleted")));
        verify(appUserService).deleteAllUsers();
    }

    @Test
    void updateUserById() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"firstname\": \"%s\",\n" +
                        "    \"password\": \"%s\",\n" +
                        "    \"address\": \"%s\",\n" +
                        "    \"email\": \"%s\",\n" +
                        "    \"lastname\": \"%s\"\n" +
                        "}"
                , input.getFirstname(), input.getPassword(), input.getAddress(), input.getEmail(), input.getLastname());
        long id = 1;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/users/" + id)
                        .contentType(APPLICATION_JSON)
                        .content(request);

        when(appUserService
                .updateUserById(testAppUser2, 1L))
                .thenReturn(testAppUser2);
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully")));
//        verify(appUserService).updateUserById(testAppUser2,1L);
    }

    @Test
    void updateAllUsers() {
    }

    @Test
    void addUser() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"firstname\": \"%s\",\n" +
                        "    \"password\": \"%s\",\n" +
                        "    \"address\": \"%s\",\n" +
                        "    \"email\": \"%s\",\n" +
                        "    \"lastname\": \"%s\"\n" +
                        "}"
                , input.getFirstname(), input.getPassword(), input.getAddress(), input.getEmail(), input.getLastname());
        when(appUserService
                .saveUser(input))
                .thenReturn(testAppUser);
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(equalTo("Created successfully")));
//        verify(appUserService).saveUser(input);
    }

    @Test
    void getUsersShouldReturnListOfUsers() throws Exception {
        when(appUserService
                .getAllUsers())
                .thenReturn(listAppUser);
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testAppUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value(testAppUser.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname").value(testAppUser.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(testAppUser.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value(testAppUser.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value(testAppUser.getPassword()));
        verify(appUserService).getAllUsers();
    }

    @Test
    void getUserShouldReturnUser() throws Exception {
        when(appUserService
                .findUserById(1L))
                .thenReturn(Optional.of(testAppUser));
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testAppUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(testAppUser.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(testAppUser.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(testAppUser.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(testAppUser.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(testAppUser.getPassword()));
        verify(appUserService).findUserById(1L);
    }
}