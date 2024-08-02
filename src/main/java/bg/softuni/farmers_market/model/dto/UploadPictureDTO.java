package bg.softuni.farmers_market.model.dto;

import bg.softuni.farmers_market.validation.annotations.FileAnnotation;
import org.springframework.web.multipart.MultipartFile;

public class UploadPictureDTO {

    private long id;
    @FileAnnotation(contentTypes = {"image/png", "image/jpeg"})
    private MultipartFile picture;

    public long getId() {
        return id;
    }

    public UploadPictureDTO setId(long id) {
        this.id = id;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public UploadPictureDTO setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
