package de.hrw.verteiltesystemepraktikum;

import de.hrw.verteiltesystemepraktikum.appuser.AppUserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VerteiltesystemepraktikumApplicationTests {

	@Autowired
	private AppUserController appUserController;

	@Test
	void contextLoads() throws Exception {
		assertThat(appUserController).isNotNull();
	}

}
