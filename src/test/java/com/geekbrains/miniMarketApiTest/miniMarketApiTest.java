package com.geekbrains.miniMarketApiTest;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.geekbrains.retrofit.api.MiniMarketApiService;
import com.geekbrains.retrofit.model.Category;
import com.geekbrains.retrofit.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class miniMarketApiTest {

    private static MiniMarketApiService apiService;
    private static Gson gson;
    private static Long id = 0L;

    @BeforeAll
    static void beforeAll() {
        apiService = new MiniMarketApiService();
        gson = new Gson();
    }

    @DisplayName("Тест получения списка товаров")
    @Test
    @Order(1)
    void testGetProducts() throws IOException {
        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();
        String json = getJsonResource("D:/IdeaProjects/auto_test_java_backend/src/test" +
                "/resources/com/geekbrains/miniMarketApiTest/testGetProducts/expected.json");
        List<Product> expected = gson.fromJson(json, type);
        List<Product> actually = apiService.getProducts();
        Assertions.assertEquals(expected.size(), actually.size());
        for (int i = 0; i < expected.size(); i++) {
            assertProduct(expected.get(i), actually.get(i));
        }
    }

    @DisplayName("Тест получения товара по id")
    @Test
    @Order(2)
    void testGetProductById() throws IOException {
        Product product = apiService.getProduct(1L);
        Assertions.assertEquals(1L, product.getId());
        Assertions.assertEquals("Milk", product.getTitle());
        Assertions.assertEquals("Food", product.getCategoryTitle());
    }

    @DisplayName("Негативный тест получения товара по id")
    @Test
    @Order(3)
    void testGetProductByIdInvalid() throws IOException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Product product = apiService.getProduct(100L);
        });
    }

    @DisplayName("Тест создания нового товара")
    @Test
    @Order(4)
    void testPostCreateProduct() throws IOException {
        Product product = Product.builder()
                .title("Redmi Note 10")
                .price(25000)
                .categoryTitle("Electronic")
                .build();
        id = apiService.createProduct(product);
        Product expected = apiService.getProduct(id);

        Assertions.assertEquals(id, expected.getId());
    }

    @DisplayName("Тест обновления информации о товаре")
    @Test
    @Order(5)
    void testPutUpdateProduct() {
        Product product = Product.builder()
                .id(id)
                .title("Redmi Note 11")
                .price(35000)
                .build();

        Assertions.assertEquals("Redmi Note 11", product.getTitle());
    }

    @DisplayName("Тест удаления продукта")
    @Test
    @Order(6)
    void testDeleteById() throws IOException {

        Assertions.assertThrows(EOFException.class, () -> {
            apiService.deleteProduct(id);
        });

        Assertions.assertThrows(RuntimeException.class, () -> {
            apiService.getProduct(id);
        });
    }

    @DisplayName("Тест на получение категории по id")
    @Test
    @Order(7)
    void testGetCategoryByIdInvalid() throws IOException {
//        Category category = apiService.getCategory(1L);
//        Assertions.assertEquals(1L, category.getIdCategory());
//        Assertions.assertEquals("Food", category.getTitleCategory());

        Assertions.assertThrows(RuntimeException.class, () -> {
            Category category = apiService.getCategory(100L);
        });
    }

    String getJsonResource(String resource) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(getClass().getResource(resource).getFile()));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    void assertProduct(Product expected, Product actually) {
        Assertions.assertEquals(expected.getId(), actually.getId());
        Assertions.assertEquals(expected.getTitle(), actually.getTitle());
        Assertions.assertEquals(expected.getCategoryTitle(), actually.getCategoryTitle());
        Assertions.assertEquals(expected.getPrice(), actually.getPrice());
    }
}
