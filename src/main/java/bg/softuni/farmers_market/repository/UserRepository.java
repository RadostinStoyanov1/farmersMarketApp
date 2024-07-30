package bg.softuni.farmers_market.repository;

import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.service.impl.UserDetailsServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
