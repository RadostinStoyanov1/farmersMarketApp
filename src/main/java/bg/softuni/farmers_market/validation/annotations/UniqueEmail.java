package bg.softuni.farmers_market.validation.annotations;

import bg.softuni.farmers_market.validation.validators.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Such Email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
