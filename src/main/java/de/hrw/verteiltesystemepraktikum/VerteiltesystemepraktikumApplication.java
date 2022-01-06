package de.hrw.verteiltesystemepraktikum;


import com.github.javafaker.Faker;
import de.hrw.verteiltesystemepraktikum.appuser.AppUser;
import de.hrw.verteiltesystemepraktikum.appuser.AppUserService;
import de.hrw.verteiltesystemepraktikum.product.Product;
import de.hrw.verteiltesystemepraktikum.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

@SpringBootApplication
@Slf4j
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class VerteiltesystemepraktikumApplication {


	@Value("${load.initial.data}")
	String generateTestData;

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

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private ProductService productService;

	// TODO: Die folgenden Zeilen müssen auskommentiert werden, wenn die Tests ausgeführt werden.
	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			if(Boolean.parseBoolean(generateTestData)) {
				for (int i = 0; i < 10; i++) {
					Faker faker = new Faker();
					String firstname = faker.name().firstName();
					String lastname = faker.name().lastName();
					String password = faker.crypto().md5();
					String address = faker.address().fullAddress();
					String email = String.format("%s.%s@%s.com", firstname, lastname, lastname);
					appUserService.saveUser(new AppUser(
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
					productService.saveProduct(new Product(
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
