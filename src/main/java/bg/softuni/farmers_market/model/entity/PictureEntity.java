package bg.softuni.farmers_market.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class PictureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private OfferEntity offer;

    public Long getId() {
        return id;
    }

    public PictureEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PictureEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public PictureEntity setOffer(OfferEntity offer) {
        this.offer = offer;
        return this;
    }
}
