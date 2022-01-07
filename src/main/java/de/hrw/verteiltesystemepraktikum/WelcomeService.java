package de.hrw.verteiltesystemepraktikum;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class WelcomeService {

    public String getWelcomeMessage(String name) {
        return String.format("Welcome %s!", name);
    }
}
