package bg.softuni.farmers_market.validation.validators;

import bg.softuni.farmers_market.model.dto.UserRegisterDTO;
import bg.softuni.farmers_market.validation.annotations.TwoPasswordsMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class TwoPasswordsValidator implements ConstraintValidator<TwoPasswordsMatch, UserRegisterDTO> {
    private String message;
    @Override
    public void initialize(TwoPasswordsMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(UserRegisterDTO dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return true;
        }

        boolean isValid = dto.getPassword().equals(dto.getConfirmPassword());

        if (!isValid) {
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;

    }
}
