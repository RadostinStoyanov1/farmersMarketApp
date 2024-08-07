package bg.softuni.farmers_market.web;

import bg.softuni.farmers_market.config.OfferApiConfig;
import bg.softuni.farmers_market.model.dto.OfferDTO;
import bg.softuni.farmers_market.model.dto.OfferDetailsDTO;
import bg.softuni.farmers_market.model.dto.OfferSummaryDTO;
import bg.softuni.farmers_market.model.entity.UserEntity;
import bg.softuni.farmers_market.model.entity.UserRoleEntity;
import bg.softuni.farmers_market.model.enums.ProductTypeEnum;
import bg.softuni.farmers_market.model.enums.UserRoleEnum;
import bg.softuni.farmers_market.service.OfferService;
import bg.softuni.farmers_market.service.PictureService;
import bg.softuni.farmers_market.service.UserService;
import bg.softuni.farmers_market.service.helper.UserHelperService;
import bg.softuni.farmers_market.service.impl.OfferServiceImpl;
import bg.softuni.farmers_market.service.impl.UserServiceImpl;
import bg.softuni.farmers_market.validation.validators.UniqueEmailValidator;
import bg.softuni.farmers_market.validation.validators.UniqueUsernameValidator;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;


import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@EnableWireMock(
        @ConfigureWireMock(name = "offers-external-api")
)
public class OfferServiceImplIT {
    private static final String DEFAULT_OFFER_PICTURE_URL = "/images/DefaultOfferPicture.jpg";
    private static final String BASE_IMAGES_PATH = "./src/main/resources/static/images/";
    private final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);
    @InjectWireMock("offers-external-api")
    private WireMockServer wireMockServer;
    @Autowired
    private OfferService offerService;
    @Autowired
    private OfferApiConfig offerApiConfig;
    @MockBean
    private UserHelperService mockUserHelperService;
    @MockBean
    private PictureService mockPictureService;
    @MockBean
    private UserServiceImpl mockUserService;

    @BeforeEach
    void SetUp() {
        offerApiConfig.setBaseUrl(wireMockServer.baseUrl());
    }
    @Test
    void testGetAllOffersAsOfferDTO() {

        wireMockServer.stubFor(get("/offers").willReturn(
                aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                """
                                        [
                                            {
                                                "id": 1,
                                                "productType": "TOMATO",
                                                "description": "Large, juicy, sweet and excellent taste tomatoes. Price per kg: 5 BNG. For contacts: +359 111 111 111",
                                                "name": "Pink Tomatoes",
                                                "author": 2
                                            },
                                            {
                                                "id": 3,
                                                "productType": "CUCUMBER",
                                                "description": "Perfect taste cucumbers. Excellent for salads. Price 3 BGN/kg. Contact me on: +359 555 555 555",
                                                "name": "Crodile Cucumbers",
                                                "author": 2
                                            }
                                        ]
                                """
                        )
                )
        );

        List<OfferDTO> offerDTOList = offerService.getALlOffersAsOfferDTO();
        Assertions.assertFalse(offerDTOList.isEmpty());
        Assertions.assertEquals(2, offerDTOList.size());

        OfferDTO offer1 = offerDTOList.get(0);
        OfferDTO offer2 = offerDTOList.get(1);

        Assertions.assertEquals(1, offer1.getId().intValue());
        Assertions.assertEquals("TOMATO", offer1.getProductType().toString());
        Assertions.assertEquals("Large, juicy, sweet and excellent taste tomatoes. Price per kg: 5 BNG. For contacts: +359 111 111 111", offer1.getDescription());
        Assertions.assertEquals("Pink Tomatoes", offer1.getName());
        Assertions.assertEquals(2, offer1.getAuthor().intValue());

        Assertions.assertEquals(3, offer2.getId().intValue());
    }

    @Test
    void testGetAllOffersSummary() {
        when(mockUserService.getUserById(2L)).thenReturn(createUserEntity());
        when(mockPictureService.getPictureUrlsByOfferId(1L)).thenReturn(createPictureUrls());
        when(mockPictureService.getPictureUrlsByOfferId(3L)).thenReturn(createPictureUrls());

        wireMockServer.stubFor(get("/offers").willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(
                                        """
                                                [
                                                    {
                                                        "id": 1,
                                                        "productType": "TOMATO",
                                                        "description": "Large, juicy, sweet and excellent taste tomatoes. Price per kg: 5 BNG. For contacts: +359 111 111 111",
                                                        "name": "Pink Tomatoes",
                                                        "author": 2
                                                    },
                                                    {
                                                        "id": 3,
                                                        "productType": "CUCUMBER",
                                                        "description": "Perfect taste cucumbers. Excellent for salads. Price 3 BGN/kg. Contact me on: +359 555 555 555",
                                                        "name": "Crodile Cucumbers",
                                                        "author": 2
                                                    }
                                                ]
                                        """
                                )
                )
        );

        List<OfferSummaryDTO> offerDTOList = offerService.getAllOffersSummary();

        Assertions.assertFalse(offerDTOList.isEmpty());
        Assertions.assertEquals(2, offerDTOList.size());

        OfferSummaryDTO offer1 = offerDTOList.get(0);
        OfferSummaryDTO offer2 = offerDTOList.get(1);

        Assertions.assertEquals(1, offer1.getId().intValue());
        Assertions.assertEquals("Pink Tomatoes", offer1.getName());
        Assertions.assertEquals("TOMATO", offer1.getProductType().toString());
        Assertions.assertEquals("Georgi Georgiev", offer1.getAuthorName());
        Assertions.assertEquals("/images/picture1.jpg", offer1.getPictureUrl());

        Assertions.assertEquals(3, offer2.getId().intValue());

    }

    @Test
    void testGetOfferDetails_Found() {
        when(mockUserService.getUserById(2L)).thenReturn(createUserEntity());
        when(mockPictureService.getPictureUrlsByOfferId(1L)).thenReturn(createPictureUrls());

        wireMockServer.stubFor(get("/offers/1").willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(
                                        """
                                             {
                                                "id": 1,
                                                "productType": "TOMATO",
                                                "description": "Large, juicy, sweet and excellent taste tomatoes. Price per kg: 5 BNG. For contacts: +359 111 111 111",
                                                "name": "Pink Tomatoes",
                                                "author": 2
                                             }
                                        """
                                )
                )
        );

        OfferDetailsDTO offerDetailsDTO = offerService.getOfferDetails(1L);

        Assertions.assertInstanceOf(OfferDetailsDTO.class, offerDetailsDTO);

        Assertions.assertEquals(1, offerDetailsDTO.getId().intValue());
        Assertions.assertEquals("Pink Tomatoes", offerDetailsDTO.getName());
        Assertions.assertEquals("TOMATO", offerDetailsDTO.getProductType().toString());
        Assertions.assertEquals("Georgi Georgiev", offerDetailsDTO.getAuthorFullName());
        Assertions.assertEquals("Large, juicy, sweet and excellent taste tomatoes. Price per kg: 5 BNG. For contacts: +359 111 111 111", offerDetailsDTO.getDescription());
        Assertions.assertEquals(2, offerDetailsDTO.getAuthorId());
        Assertions.assertEquals( List.of("/images/picture1.jpg", "/images/picture2.jpg"), offerDetailsDTO.getPictures());

    }

    @Test
    void testCanUpload() {
        when(mockUserHelperService.getUser()).thenReturn(createUserEntity());
        OfferDetailsDTO offerDetailsDTO = new OfferDetailsDTO()
                .setId(1L)
                .setDescription("Large, juicy, sweet and excellent taste tomatoes. Price per kg: 5 BNG. For contacts: +359 111 111 111")
                .setProductType(ProductTypeEnum.TOMATO)
                .setName("Pink Tomatoes")
                .setPictures(List.of("/images/picture1.jpg", "/images/picture2.jpg"))
                .setAuthorId(2L)
                .setAuthorFullName("Georgi Georgiev");

        boolean result = offerService.canUpload(offerDetailsDTO);

        Assertions.assertTrue(result);
    }

    @Test
    void testCanDelete() {
        when(mockUserHelperService.getUser()).thenReturn(createUserEntity());
        when(mockUserHelperService.hasRole("ADMIN")).thenReturn(true);
        OfferDetailsDTO offerDetailsDTO = new OfferDetailsDTO()
                .setId(1L)
                .setDescription("Large, juicy, sweet and excellent taste tomatoes. Price per kg: 5 BNG. For contacts: +359 111 111 111")
                .setProductType(ProductTypeEnum.TOMATO)
                .setName("Pink Tomatoes")
                .setPictures(List.of("/images/picture1.jpg", "/images/picture2.jpg"))
                .setAuthorId(2L)
                .setAuthorFullName("Georgi Georgiev");

        boolean result = offerService.canDelete(offerDetailsDTO);

        Assertions.assertTrue(result);
    }

    UserEntity createUserEntity() {
        return new UserEntity()
                .setId(2L)
                .setEmail("georgi@test.com")
                .setUsername("Goro1")
                .setFirstName("Georgi")
                .setLastName("Georgiev")
                .setPassword("12345")
                .setRoles(List.of(
                        new UserRoleEntity()
                                .setId(1L)
                                .setRole(UserRoleEnum.USER),
                        new UserRoleEntity()
                                .setId(2L)
                                .setRole(UserRoleEnum.ADMIN)
                ));
    }

    List<String> createPictureUrls() {
        return List.of("/images/picture1.jpg", "/images/picture2.jpg");
    }
}
