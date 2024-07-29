package bg.softuni.farmers_market.validation.validators;

import bg.softuni.farmers_market.service.impl.UserServiceImpl;
import bg.softuni.farmers_market.validation.annotations.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserServiceImpl userService;

    public UniqueEmailValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true;
        }

        return userService.isEmailUnique(email);

    }
}
