package com.geekbrains.mybatis;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.Products;
import com.geekbrains.db.model.ProductsExample;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        MyBatisDbService myBatisDbService = new MyBatisDbService();
        ProductsMapper productsMapper = myBatisDbService.getProductsMapper();
        CategoriesMapper categoriesMapper = myBatisDbService.getCategotiesMapper();

        Products products = productsMapper.selectByPrimaryKey(1L);
        System.out.println(products);

        ProductsExample productsExample = new ProductsExample();
        productsExample.createCriteria().andPriceLessThan(1000);
        List<Products> productsList = productsMapper.selectByExample(productsExample);
        System.out.println(productsList);

        ProductsExample productsExampleCategoryId = new ProductsExample();
        productsExampleCategoryId.createCriteria().andCategoryIdEqualTo(1L);
        List<Products> productsListCategoryId =
                productsMapper.selectByExample(productsExampleCategoryId);
        System.out.println(productsListCategoryId);

        ProductsExample productsExampleTitle = new ProductsExample();
        productsExampleTitle.createCriteria().andTitleBetween("A", "H");
        List<Products> productsListTitleBetween =
                productsMapper.selectByExample(productsExampleTitle);
        System.out.println(productsListTitleBetween);


//        Categories categoriesCreate = new Categories();
//        categoriesCreate.setTitle("Shoes");
//
//        myBatisDbService.addCategories(categoriesCreate);
//        Categories categories = categoriesMapper.selectByPrimaryKey(3L);
//        System.out.println(categories);

//        Products productsCreate = Products.builder()
//                .title("Boots")
//                .price(2500)
//                .categoryId(3L)
//                .build();
//
//        Products productsCreate1 = Products.builder()
//                .title("Sneakers")
//                .price(1500)
//                .categoryId(3L)
//                .build();
//        Products productsCreate2 = Products.builder()
//                .title("Slippers")
//                .price(500)
//                .categoryId(3L)
//                .build();
//
//        myBatisDbService.addProduct(productsCreate);
//        myBatisDbService.addProduct(productsCreate1);
//        myBatisDbService.addProduct(productsCreate2);
//
    }
}
