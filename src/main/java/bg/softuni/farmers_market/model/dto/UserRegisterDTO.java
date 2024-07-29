package bg.softuni.farmers_market.model.dto;

import bg.softuni.farmers_market.validation.annotations.TwoPasswordsMatch;
import bg.softuni.farmers_market.validation.annotations.UniqueEmail;
import bg.softuni.farmers_market.validation.annotations.UniqueUsername;
import jakarta.validation.constraints.*;

@TwoPasswordsMatch
public class UserRegisterDTO {
    @NotBlank
    @Size(min = 2, max = 200)
    @UniqueUsername
    private String username;

    @NotEmpty
    @Size(min = 5, max = 200)
    private String firstName;
    @NotEmpty
    @Size(min = 5, max = 200)
    private String lastName;

    @Email(regexp = ".*@.*")
    @UniqueEmail
    private String email;

    @Size(min = 5, max = 100)
    private String password;

    @Size(min = 5, max = 100)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
