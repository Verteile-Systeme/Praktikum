package de.hrw.verteiltesystemepraktikum;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class VerteiltesystemepraktikumApplication {

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
