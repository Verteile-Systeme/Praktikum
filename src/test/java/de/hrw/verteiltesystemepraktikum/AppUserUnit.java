package de.hrw.verteiltesystemepraktikum;

import de.hrw.verteiltesystemepraktikum.appuser.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
public class AppUserUnit {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorinstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void whenNotNullName_thenConstraintViolations() {
        AppUser appUser = new AppUser();

        Set<ConstraintViolation<AppUser>> violations =
                validator.validate(appUser);

        violations.forEach(violation -> log.info(String.valueOf(violation)));

        assertThat(violations.size()).isEqualTo(5);
    }
}
