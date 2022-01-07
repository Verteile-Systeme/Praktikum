package de.hrw.verteiltesystemepraktikum.appuser;

import com.github.javafaker.App;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserControllerUnitTest {

    AppUserService appUserService = Mockito.mock(AppUserService.class);
    AppUserController appUserController = new AppUserController(appUserService);

    AppUser testAppUser = new AppUser(
            1L,
            "testfirstname",
            "testlastname",
            "test@email.de",
            "testaddress",
            "testpassword"
    );

    AppUser updatetestAppUser = new AppUser(
            1L,
            "testfirstnameUpdate",
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

    List<AppUser> listAppUser = Arrays.asList(testAppUser, testAppUser2);

    AppUser input = new AppUser(
            "testfirstname",
            "testlastname",
            "test@email.de",
            "testaddress",
            "testpassword"
    );

    @Test
    void deleteUserById() {
        doNothing().when(appUserService).deleteUserById(1L);
        assertEquals("Deleted successfully",appUserController.deleteUserById(1L));
    }

    @Test
    void deleteAllUsers() {
        when(appUserService.deleteAllUsers()).thenReturn(5L);
        assertEquals("5 Entities deleted",appUserController.deleteAllUsers());
    }

    @Test
    void updateUserById() {
        when(appUserService.updateUserById(updatetestAppUser, 1L)).thenReturn(updatetestAppUser);
        assertEquals("Updated successfully",appUserController.updateUserById(1L, updatetestAppUser));
    }

    @Test
    void updateAllUsers() {
        when(appUserService.updateAllUsers(updatetestAppUser)).thenReturn(listAppUser);
        assertEquals("Updated successfully",appUserController.updateAllUsers(updatetestAppUser));
    }

    @Test
    void addUser() {
        when(appUserService.saveUser(input)).thenReturn(testAppUser);
        assertEquals("Created successfully",appUserController.addUser(input));
    }

    @Test
    void getUsers() {
        when(appUserService.getAllUsers()).thenReturn(listAppUser);
        assertEquals(listAppUser,appUserController.getUsers());
    }

    @Test
    void getUser() {
        Optional<AppUser> optionalAppUser = Optional.of(testAppUser);
        when(appUserService.findUserById(1L)).thenReturn(optionalAppUser);
        assertEquals(optionalAppUser,appUserController.getUser(1L));
    }
}