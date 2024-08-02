package bg.softuni.farmers_market.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "offers")
public class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_type_id", referencedColumnName = "id")
    private ProductTypeEntity productType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "name", nullable = false)
    private String name;
    /*@OneToMany(targetEntity = PictureEntity.class, mappedBy = "offer")
    private List<PictureEntity> pictures;*/

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity author;
    @Column(name = "likes")
    private Long likes;

    public OfferEntity() {
        likes = 0l;
        //pictures = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public OfferEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferEntity setName(String name) {
        this.name = name;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public OfferEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public ProductTypeEntity getProductType() {
        return productType;
    }

    public OfferEntity setProductType(ProductTypeEntity productType) {
        this.productType = productType;
        return this;
    }

    public Long getLikes() {
        return likes;
    }

    public OfferEntity setLikes(Long likes) {
        this.likes = likes;
        return this;
    }

    /*public List<PictureEntity> getPictures() {
        return pictures;
    }

    public OfferEntity setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }*/
}
