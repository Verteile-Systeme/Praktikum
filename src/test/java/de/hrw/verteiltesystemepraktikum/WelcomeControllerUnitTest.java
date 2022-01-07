package de.hrw.verteiltesystemepraktikum;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class WelcomeControllerUnitTest {

    @Test
    void shouldWelcome() {
        WelcomeService welcomeService = Mockito.mock(WelcomeService.class);
        when(welcomeService.getWelcomeMessage("John")).thenReturn("Welcome John!");
        WelcomeController welcomeController = new WelcomeController(welcomeService);
        assertEquals("Welcome John!", welcomeController.welcome("John"));
    }
}