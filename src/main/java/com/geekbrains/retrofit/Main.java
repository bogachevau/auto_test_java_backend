package com.geekbrains.retrofit;

import com.geekbrains.retrofit.api.MiniMarketApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8189/market/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MiniMarketApi api = retrofit.create(MiniMarketApi.class);
//        Call<Object> productsCall = api.getProducts();
//        Response<Object> response = productsCall.execute();
//        System.out.println(response.body());
    }
}
