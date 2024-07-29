package bg.softuni.farmers_market.repository;

import bg.softuni.farmers_market.model.entity.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Long> {
}
