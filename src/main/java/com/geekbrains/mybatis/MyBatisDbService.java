package com.geekbrains.mybatis;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.Products;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisDbService {

    private SqlSession session;

    public MyBatisDbService() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(getClass().getResourceAsStream("mybatis-config.xml"));
        session = sqlSessionFactory.openSession();
    }

    public ProductsMapper getProductsMapper() {
        return session.getMapper(ProductsMapper.class);
    }

    public void addProduct(Products productsParam) {
        session.insert("mini-market.mv.db", productsParam);
        session.commit();
        session.close();
    }

    public void addCategories(Categories categoriesParam) {
        session.insert("com.geekbrains.db.model.Products", categoriesParam);
        session.commit();
        session.close();
    }

    public CategoriesMapper getCategotiesMapper() {
        return session.getMapper(CategoriesMapper.class);
    }
}
