package de.hrw.verteiltesystemepraktikum;

import de.hrw.verteiltesystemepraktikum.appuser.AppUser;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class VerteiltesystemepraktikumApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerteiltesystemepraktikumApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(AppUserRepository appUserRepository) {
//		return args -> {
//			appUserRepository.save(new AppUser(
//					"Dimitrios",
//					"Barkas",
//					"dimitrios.barkas@codecentric.de",
//					"1234"
//			));
//
//			System.out.println(appUserRepository.existsByEmail("dimitrios.barkas@codecentric.de") == true ? "" +
//					"Exists" : "Don`t exists.");
//		};
//	}

}
