package com.geekbrains.restApi.imgur;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.core.Is.is;

public class ImgurApiParams {

    public static String TOKEN = "5fcfba6a5ce27a9ebf76f6843d39cc248157d8bd";
    public static String URL_API = "https://api.imgur.com";
    public static String API_VERSION = "3";
    public static String USER_NAME = "alexandrbogachew";
    public static String CLIENT_ID = "d7898aa4c8a51a7";
    public static String IMAGE_ID = "Ubo2bsr";
    public static String IMAGE_HASH = "Ubo2bsr";
    public static String IMAGE_HASH_2 = "eRvAhuK";
    public static String IMAGE_HASH_3 = "EW0nF48";
    public static String IMAGE_HASH_4 = "T5GnsFM";
    public static int PAGE = 0;
    public static String ALBUM_HASH = "FiCKWlR";
    public static String IMAGE_DELETE_HASH = "Fgy3bEhzZDpo2Za";
    public static String ALBUM_DELETE_HASH = "4sYopnBBXfCgP8D";
    public static String URL_ALBUM_PUT = "/album/" + ImgurApiParams.ALBUM_HASH;

    public static ResponseSpecification responseSpecificationStatusDataSuccess = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("data", is(true))
            .expectBody("success", is(true))
            .build();
}
