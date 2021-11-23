package com.geekbrains.restApi.imgur;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ImgurApiTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParams.URL_API + "/" + ImgurApiParams.API_VERSION;
    }

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
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data.bio", is("any system is vulnerable"))
                .body("data.reputation", is(0))
                .log()
                .all()
                .when()
                .get(url);
    }

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
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data.account_id", is(156802485))
                .body("data.deletehash", is("enMuXzjUAlwX83C"))
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
                .statusCode(is(200))
                .body("data", is(true))
                .body("success", is(true))
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

    @DisplayName("Тест на получение информации об альбоме")
    @Test
    @Order(5)
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
                .statusCode(is(200))
                .body("data.title", is("Warhammer 40k"))
                .body("data.images_count", is(2))
                .body("data.deletehash", is("gjqU8ZG5a8z6XNG"))
                .when()
                .get(url);
    }

    @DisplayName("Тест на получение информации об альбоме")
    @Test
    @Order(6)
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
                .statusCode(is(200))
                .body("success", is(true))
                .body("data.title", is("Warhammer 40k"))
                .body("data.images_count", is(2))
                .body("data.id", is("FiCKWlR"))
                .when()
                .get(url);
    }

    @DisplayName("Тест на получение информации об изображении в альбоме")
    @Test
    @Order(7)
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

    @DisplayName("Тест создания нового альбома")
    @Test
    @Order(8)
    void testAlbumCreationPost() {
        String url = "/album";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("ids[]", ImgurApiParams.IMAGE_HASH_2)
                .formParam("ids[]", ImgurApiParams.IMAGE_HASH_3)
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
    @Order(9)
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
    @Order(10)
    void testAlbumUpdatePut() {
        String url = "/album/" + ImgurApiParams.ALBUM_HASH;
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
                .statusCode(is(200))
                .body("data", is(true))
                .body("success", is(true))
                .when()
                .put(url);
    }
}
