package bg.softuni.farmers_market.service;

import bg.softuni.farmers_market.model.dto.UserRegisterDTO;
import bg.softuni.farmers_market.model.entity.UserEntity;

public interface UserService {
    public boolean isUsernameUnique(String username);
    public boolean isEmailUnique(String email);

    public void register(UserRegisterDTO userRegisterDTO);

    public UserEntity getUserById(Long id);
}
