package com.geekbrains.retrofit.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Category {
    private String titleCategory;
    private Long idCategory;
    private List<Product> productsInCategory;
}
