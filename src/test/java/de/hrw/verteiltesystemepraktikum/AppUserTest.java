package de.hrw.verteiltesystemepraktikum;

import org.junit.BeforeClass;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.xml.validation.Validator;

public class AppUserTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) factory.getValidator();
    }


}
