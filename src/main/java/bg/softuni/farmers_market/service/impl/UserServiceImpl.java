package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.dto.UserRegisterDTO;
import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.model.enums.UserRoleEnum;
import bg.softuni.farmers_market.repository.UserRepository;
import bg.softuni.farmers_market.repository.UserRoleRepository;
import bg.softuni.farmers_market.service.UserService;
import bg.softuni.farmers_market.service.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public boolean isUsernameUnique(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        UserEntity user = this.modelMapper.map(userRegisterDTO, UserEntity.class);

        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.getRoles().add(userRoleRepository.getUserRoleEntityByRole(UserRoleEnum.USER));

        userRepository.save(user);
    }

    public UserEntity getUserByUsername(String email) {
        return userRepository
                .findByUsername(email)
                .orElseThrow(
                () -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
