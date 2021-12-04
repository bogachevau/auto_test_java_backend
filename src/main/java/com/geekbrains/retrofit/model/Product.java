package com.geekbrains.retrofit.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
    private Long id;
    private String title;
    private Integer price;
    private String categoryTitle;
}
