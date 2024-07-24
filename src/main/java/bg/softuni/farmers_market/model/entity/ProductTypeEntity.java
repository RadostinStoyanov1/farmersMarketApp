package bg.softuni.farmers_market.model.entity;

import bg.softuni.farmers_market.model.enums.ProductTypeEnum;
import bg.softuni.farmers_market.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
@Entity
@Table(name = "products")

public class ProductTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductTypeEnum productType;
}
