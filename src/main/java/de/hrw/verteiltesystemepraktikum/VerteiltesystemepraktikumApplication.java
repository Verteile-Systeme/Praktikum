package de.hrw.verteiltesystemepraktikum;


import com.github.javafaker.Faker;
import de.hrw.verteiltesystemepraktikum.appuser.AppUser;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class VerteiltesystemepraktikumApplication {

	@Value(value = "${load.initial.data}")
	boolean genrateTestdata;

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	public static void main(String[] args) {
		SpringApplication.run(VerteiltesystemepraktikumApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AppUserRepository appUserRepository) {
		return args -> {
			if(genrateTestdata) {
				for (int i = 0; i < 10; i++) {
					Faker faker = new Faker();
					String firstname = faker.name().firstName();
					String lastname = faker.name().lastName();
					String password = faker.crypto().md5();
					String address = faker.address().fullAddress();
					String email = String.format("%s.%s@%s.com", firstname, lastname, lastname);
					appUserRepository.save(new AppUser(
							firstname,
							lastname,
							email,
							address,
							password
					));
				}

			}
		};
	}

}
