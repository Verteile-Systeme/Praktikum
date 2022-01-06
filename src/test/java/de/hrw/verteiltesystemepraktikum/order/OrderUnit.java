package de.hrw.verteiltesystemepraktikum.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class OrderUnit {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorinstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void whenOrderWrongInit_thenConstraintViolations() {
        Order order = new Order();

        Set<ConstraintViolation<Order>> violations =
                validator.validate(order);

        violations.forEach(violation -> log.info(String.valueOf(violation)));

        assertThat(violations.size()).isEqualTo(2);
    }


}
