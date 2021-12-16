package com.geekbrains.restApi.imgur;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ImgurApiTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParams.URL_API + "/" + ImgurApiParams.API_VERSION;
    }

    ResponseSpecification responseSpecificationBaseAccountInfo = new ResponseSpecBuilder()
            .expectBody("success", is(true))
            .expectBody("status", is(200))
            .expectBody("data.bio", is("any system is vulnerable"))
            .expectBody("data.reputation", is(0))
            .expectStatusCode(200)
            .build();

    @DisplayName("Тест на получение базовой информации об аккаунте")
    @Test
    @Order(1)
    void testAccountBase() {
        String url = "account/" + ImgurApiParams.USER_NAME;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .spec(responseSpecificationBaseAccountInfo)
                .log()
                .all()
                .when()
                .get(url);
    }

    ResponseSpecification responseSpecificationImageInfo = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("success", is(true))
            .expectBody("status", is(200))
            .expectBody("data.account_id", is(156802485))
            .expectBody("data.deletehash", is("enMuXzjUAlwX83C"))
            .build();

    @DisplayName("Тест на получение информации о картинке")
    @Test
    @Order(2)
    void testAccountImage() {
        String url = "account/" + ImgurApiParams.USER_NAME + "/image/" + ImgurApiParams.IMAGE_ID;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .spec(responseSpecificationImageInfo)
                .log()
                .all()
                .when()
                .get(url);
    }

    @DisplayName("Тест обновления информации о картинке")
    @Test
    @Order(3)
    void testUpdateImageInfo() {
        String url = "/image/" + ImgurApiParams.IMAGE_HASH;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "Astartes")
                .formParam("description", "Just a simple mem")
                .expect()
                .log()
                .all()
                .spec(ImgurApiParams.responseSpecificationStatusDataSuccess)
                .when()
                .post(url);
    }

    @DisplayName("Тест получения информации об альбомах пользователя")
    @Test
    @Order(4)
    void testAccountAlbumsUserInfo() {
        String url = "/account/" + ImgurApiParams.USER_NAME + "/albums/" + ImgurApiParams.PAGE;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .get(url);
    }

    @DisplayName("Тест на обновление информации об альбоме")  // Этот тест нужен чтобы следующие 2 не падали
    @Test
    @Order(5)
    void testAlbumUpdateInfoPut() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log().all()
                .formParam("title", "Warhammer 40k")
                .formParam("description", "Testing Test")
                .expect()
                .log().all()
                .spec(ImgurApiParams.responseSpecificationStatusDataSuccess)
                .when()
                .put(ImgurApiParams.URL_ALBUM_PUT);

    }

    ResponseSpecification responseSpecificationAlbumInfo = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("data.title", is("Warhammer 40k"))
            .expectBody("data.images_count", is(2))
            .expectBody("success", is(true))
            .build();

    @DisplayName("Тест на получение информации об альбоме")
    @Test
    @Order(6)
    void testAccountAlbumInfo() {
        String url = "/account/" + ImgurApiParams.USER_NAME + "/album/" + ImgurApiParams.ALBUM_HASH;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .log()
                .all()
                .spec(responseSpecificationAlbumInfo)
                .body("data.deletehash", is("gjqU8ZG5a8z6XNG"))
                .when()
                .get(url);
    }

    @DisplayName("Тест на получение информации об альбоме")
    @Test
    @Order(7)
    void testAlbumAlbumInfo() {
        String url = "/album/" + ImgurApiParams.ALBUM_HASH;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .log()
                .all()
                .spec(responseSpecificationAlbumInfo)
                .body("data.id", is("FiCKWlR"))
                .when()
                .get(url);
    }

    @DisplayName("Тест на получение информации об изображении в альбоме")
    @Test
    @Order(8)
    void testAlbumAlbumImage() {
        String url = "/album/" + ImgurApiParams.ALBUM_HASH + "/image/" + ImgurApiParams.IMAGE_HASH;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("data.id", is("Ubo2bsr"))
                .body("data.link", is("https://i.imgur.com/Ubo2bsr.jpg"))
                .when()
                .get(url);
    }

    RequestSpecification requestSpecificationAlbumCreation = new RequestSpecBuilder()
            .addFormParam("ids[]", ImgurApiParams.IMAGE_HASH_2)
            .addFormParam("ids[]", ImgurApiParams.IMAGE_HASH_3)
            .build();

    @DisplayName("Тест создания нового альбома")
    @Test
    @Order(9)
    void testAlbumCreationPost() {
        String url = "/album";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .spec(requestSpecificationAlbumCreation)
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .post(url);
    }

    @DisplayName("Тест добавления альбома в избранное")
    @Test
    @Order(10)
    void testAlbumFavoriteAlbumPost() {
        String url = "/album/" + ImgurApiParams.ALBUM_HASH + "/favorite";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .post(url).jsonPath().getString("data").contains("favorited");
    }

    @DisplayName("Тест на обновление информации об альбоме")
    @Test
    @Order(11)
    void testAlbumUpdatePut() {
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "Adeptus Astartes")
                .formParam("description", "Any system is vulnerable")
                .expect()
                .log()
                .all()
                .spec(ImgurApiParams.responseSpecificationStatusDataSuccess)
                .when()
                .put(ImgurApiParams.URL_ALBUM_PUT);
    }
}
