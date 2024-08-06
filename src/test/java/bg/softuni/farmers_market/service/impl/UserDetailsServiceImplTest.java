package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.model.entity.UserRoleEntity;
import bg.softuni.farmers_market.model.enums.UserRoleEnum;
import bg.softuni.farmers_market.model.user.FarmersUserDetails;
import bg.softuni.farmers_market.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    private static final String TEST_EMAIL = "user@example.com";
    private static final String NOT_EXISTENT_EMAIL = "missing@example.com";

    private UserDetailsServiceImpl toTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new UserDetailsServiceImpl(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        UserEntity testUser = new UserEntity()
                .setEmail(TEST_EMAIL)
                .setPassword("12345")
                .setFirstName("Georgi")
                .setLastName("Georgiev")
                .setRoles(List.of(
                        new UserRoleEntity().setRole(UserRoleEnum.ADMIN),
                        new UserRoleEntity().setRole(UserRoleEnum.USER)
                ));

        when(mockUserRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername(TEST_EMAIL);

        Assertions.assertInstanceOf(FarmersUserDetails.class, userDetails);

        FarmersUserDetails farmersUserDetails = (FarmersUserDetails) userDetails;

        Assertions.assertEquals(TEST_EMAIL, farmersUserDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUser.getFirstName(), farmersUserDetails.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), farmersUserDetails.getLastName());
        Assertions.assertEquals(testUser.getFirstName() + " " + testUser.getLastName(), farmersUserDetails.getFullName());

        List<String> expectedRoles = testUser.getRoles().stream().map(r -> "ROLE_" + r.getRole()).toList();
        List<String> actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Assertions.assertEquals(expectedRoles, actualRoles);

    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> toTest.loadUserByUsername(NOT_EXISTENT_EMAIL));
    }
}
