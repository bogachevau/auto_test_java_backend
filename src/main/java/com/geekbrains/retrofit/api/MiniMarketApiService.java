package com.geekbrains.retrofit.api;

import com.geekbrains.retrofit.model.Category;
import com.geekbrains.retrofit.model.Product;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class MiniMarketApiService {

    private final MiniMarketApi api;


    public MiniMarketApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8189/market/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MiniMarketApi.class);
    }

    public List<Product> getProducts() throws IOException {
        return api.getProducts()
                .execute()
                .body();
    }

    public Product getProduct(Long id) throws IOException {
        Response<Product> productResponse = api.getProduct(id).execute();
        if (productResponse.isSuccessful()) {
            return productResponse.body();
        } else {
            throw new RuntimeException(productResponse.errorBody().string());
        }
    }

    public Long createProduct(Product product) throws IOException {
        return api.createProduct(product)
                .execute()
                .body()
                .getId();
    }

    public void updateProduct(Product product) throws IOException {
        api.updateProduct(product).execute();
    }

    public void deleteProduct(Long id) throws IOException {
        api.deleteProduct(id).execute();
    }

    public Category getCategory(Long idCategory) throws IOException {
        Response<Category> categoryResponse = api.getCategory(idCategory).execute();
        if (categoryResponse.isSuccessful()) {
            return categoryResponse.body();
        } else {
            throw new RuntimeException(categoryResponse.errorBody().string());
        }
    }
}
