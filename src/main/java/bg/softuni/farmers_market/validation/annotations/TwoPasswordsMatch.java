package bg.softuni.farmers_market.validation.annotations;

import bg.softuni.farmers_market.validation.validators.TwoPasswordsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = TwoPasswordsValidator.class)
public @interface TwoPasswordsMatch {
    String message() default "Passwords doesn't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
