package de.hrw.verteiltesystemepraktikum;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Slf4j
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class VerteiltesystemepraktikumApplication {
	public static void main(String[] args) {
		SpringApplication.run(VerteiltesystemepraktikumApplication.class, args);
	}
}
