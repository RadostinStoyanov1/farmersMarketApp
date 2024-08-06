package bg.softuni.farmers_market.web;

import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(post("/users/register")
                .param("username", "Goro1")
                .param("email", "georgi@test.com")
                .param("firstName", "Georgi")
                .param("lastName", "Georgiev")
                .param("password", "12345")
                .param("confirmPassword", "12345")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));

        Optional<UserEntity> userEntityOpt = userRepository.findByEmail("georgi@test.com");

        Assertions.assertTrue(userEntityOpt.isPresent());

        UserEntity actualUser = userEntityOpt.get();

        Assertions.assertEquals("Goro1", actualUser.getUsername());
        Assertions.assertEquals("Georgi", actualUser.getFirstName());
        Assertions.assertEquals("Georgiev", actualUser.getLastName());
        Assertions.assertTrue(passwordEncoder.matches("12345", actualUser.getPassword()));
    }
}
