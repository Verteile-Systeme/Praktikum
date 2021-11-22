package de.hrw.verteiltesystemepraktikum;


import com.github.javafaker.Faker;
import de.hrw.verteiltesystemepraktikum.appuser.AppUser;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserRepository;
import de.hrw.verteiltesystemepraktikum.product.Product;
import de.hrw.verteiltesystemepraktikum.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@Slf4j
public class VerteiltesystemepraktikumApplication {

	private static Logger logger = LoggerFactory.getLogger(VerteiltesystemepraktikumApplication.class);

	@Value(value = "${load.initial.data}")
	String genrateTestdata;

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

	// TODO: Die folgenden Zeilen müssen auskommentiert werden, wenn die Tests ausgeführt werden.
	@Bean
	CommandLineRunner commandLineRunner(AppUserRepository appUserRepository, ProductRepository productRepository) {
		return args -> {
			if(Boolean.parseBoolean(genrateTestdata)) {
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

				for (int i = 0; i < 10; i++) {
					Faker faker = new Faker();
					String name = faker.commerce().productName();
					String brand = faker.commerce().department();
					int newPrice = Integer.parseInt(faker.commerce().price().replace(".", ""));
					int oldPrice = Integer.parseInt(faker.commerce().price().replace(".", ""));
					productRepository.save(new Product(
							name,
							brand,
							newPrice,
							oldPrice
					));
				}
			}
		};
	}

}
