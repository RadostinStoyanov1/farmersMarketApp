package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.dto.UserRegisterDTO;
import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.model.entity.UserRoleEntity;
import bg.softuni.farmers_market.model.enums.UserRoleEnum;
import bg.softuni.farmers_market.repository.UserRepository;
import bg.softuni.farmers_market.repository.UserRoleRepository;
import bg.softuni.farmers_market.service.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final String ENCODED_PASSWORD = "qwerty";
    private UserServiceImpl toTest;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;
    @Mock
    private UserRoleRepository mockUserRoleRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void init() {
        toTest = new UserServiceImpl(
                mockUserRepository,
                mockPasswordEncoder,
                new ModelMapper(),
                mockUserRoleRepository
        );
    }

    @Test
    void testUserRegistration() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO()
                .setEmail("georgi@test.com")
                .setUsername("Goro1")
                .setFirstName("Georgi")
                .setLastName("Georgiev")
                .setPassword("12345")
                .setConfirmPassword("12345");

        UserRoleEntity userRoleEntity = new UserRoleEntity()
                .setRole(UserRoleEnum.USER);

        when(mockUserRoleRepository.getUserRoleEntityByRole(UserRoleEnum.USER))
                .thenReturn(userRoleEntity);

        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword()))
                .thenReturn("qwerty");

        toTest.register(userRegisterDTO);

        verify(mockUserRepository).save(userEntityCaptor.capture());

        UserEntity actualSevedEntity = userEntityCaptor.getValue();

        Assertions.assertNotNull(actualSevedEntity);
        Assertions.assertEquals(userRegisterDTO.getEmail(), actualSevedEntity.getEmail());
        Assertions.assertEquals(userRegisterDTO.getUsername(), actualSevedEntity.getUsername());
        Assertions.assertEquals(userRegisterDTO.getFirstName(), actualSevedEntity.getFirstName());
        Assertions.assertEquals(userRegisterDTO.getLastName(), actualSevedEntity.getLastName());
        Assertions.assertEquals(ENCODED_PASSWORD, actualSevedEntity.getPassword());
        Assertions.assertEquals(1, actualSevedEntity.getRoles().size());
        Assertions.assertEquals(userRoleEntity, actualSevedEntity.getRoles().get(0));
    }

    @Test
    void testGetUserById_FoundUser() {
        UserEntity expectedUser = new UserEntity()
                .setId(1l)
                .setEmail("georgi@test.com")
                .setUsername("Goro1")
                .setFirstName("Georgi")
                .setLastName("Georgiev")
                .setPassword("12345")
                .setRoles(List.of(
                        new UserRoleEntity()
                                .setId(1l)
                                .setRole(UserRoleEnum.USER),
                        new UserRoleEntity()
                                .setId(2l)
                                .setRole(UserRoleEnum.ADMIN)
                ));

        when(mockUserRepository.findById(1l)).thenReturn(Optional.of(expectedUser));

        UserEntity actualUser = toTest.getUserById(1l);

        Assertions.assertEquals(expectedUser.getId(), actualUser.getId());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        Assertions.assertEquals(expectedUser.getLastName(), actualUser.getLastName());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());

        List<String> expectedRoles = expectedUser.getRoles().stream().map(r -> r.getRole().toString()).toList();
        List<String> actualRoles = actualUser.getRoles().stream().map(r -> r.getRole().toString()).toList();

        Assertions.assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testGetUserById_UserNotFound() {
        Assertions.assertThrows(UserNotFoundException.class, () -> toTest.getUserById(2l));
    }
}
