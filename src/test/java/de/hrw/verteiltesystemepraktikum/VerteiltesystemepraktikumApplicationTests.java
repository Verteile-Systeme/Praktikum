package de.hrw.verteiltesystemepraktikum;

import de.hrw.verteiltesystemepraktikum.appuser.AppUserController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class VerteiltesystemepraktikumApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(VerteiltesystemepraktikumApplicationTests.class);

	@Autowired
	private AppUserController appUserController;

	@Test
	void contextLoads() throws Exception {
		assertThat(appUserController).isNotNull();
	}

}
