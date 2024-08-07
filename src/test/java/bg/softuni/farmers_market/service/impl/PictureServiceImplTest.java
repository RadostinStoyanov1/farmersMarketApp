package bg.softuni.farmers_market.service.impl;

import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.entity.PictureEntity;
import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;
import bg.softuni.farmers_market.repository.PictureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PictureServiceImplTest {

    @Captor
    private ArgumentCaptor<PictureEntity> pictureEntityArgumentCaptor;

    private PictureServiceImpl toTest;

    @Mock
    private PictureRepository mockPictureRepository;

    @BeforeEach
    void setUp() {
        toTest = new PictureServiceImpl(mockPictureRepository);
    }

    @Test
    void testGetPictureUrlsByOfferId() {
        List<PictureEntity> picturesList = List.of(createPictureEntity1(), createPictureEntity2());
        when(mockPictureRepository.findAllByOfferId(1L)).thenReturn(picturesList);

        List<String> actualPictureUrls = toTest.getPictureUrlsByOfferId(1L);

        Assertions.assertFalse(actualPictureUrls.isEmpty());
        Assertions.assertEquals(2, actualPictureUrls.size());
        Assertions.assertEquals("pic1.jpg", actualPictureUrls.get(0));
        Assertions.assertEquals("pic2.jpg", actualPictureUrls.get(1));
    }

    @Test
    void testCreatePicture() {
        OfferDetailsDTO offerDetailsDTO = new OfferDetailsDTO()
                .setId(1L)
                .setDescription("Large, juicy, sweet and excellent taste tomatoes. Price per kg: 5 BNG. For contacts: +359 111 111 111")
                .setProductType(ProductTypeEnum.TOMATO)
                .setName("Pink Tomatoes")
                .setPictures(List.of("/images/picture1.jpg", "/images/picture2.jpg"))
                .setAuthorId(2L)
                .setAuthorFullName("Georgi Georgiev");

        String picturePath = "/images/pic1.jpg";

        PictureEntity expectedPicture = new PictureEntity()
                .setId(1L)
                .setTitle("Pink Tomatoes")
                .setUrl("/images/pic1.jpg")
                .setOfferId(1L);

        toTest.create(offerDetailsDTO, picturePath);

        verify(mockPictureRepository).saveAndFlush(pictureEntityArgumentCaptor.capture());
        PictureEntity actualPicture = pictureEntityArgumentCaptor.getValue();

        Assertions.assertNotNull(actualPicture);
        Assertions.assertEquals(null, actualPicture.getId());
        Assertions.assertEquals(expectedPicture.getTitle(), actualPicture.getTitle());
        Assertions.assertEquals(expectedPicture.getUrl(), actualPicture.getUrl());
        Assertions.assertEquals(expectedPicture.getOfferId(), actualPicture.getOfferId());
    }

    PictureEntity createPictureEntity1() {
        return new PictureEntity()
                .setId(1L)
                .setTitle("Tomatoes")
                .setUrl("pic1.jpg")
                .setOfferId(1L);
    }
    PictureEntity createPictureEntity2() {
        return new PictureEntity()
                .setId(2L)
                .setTitle("Tomatoes")
                .setUrl("pic2.jpg")
                .setOfferId(1L);
    }

}
