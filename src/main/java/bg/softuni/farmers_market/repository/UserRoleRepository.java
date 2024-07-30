package bg.softuni.farmers_market.repository;

import bg.softuni.farmers_market.model.entity.UserRoleEntity;
import bg.softuni.farmers_market.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    public UserRoleEntity getUserRoleEntityByRole(UserRoleEnum roleEnum);
}
