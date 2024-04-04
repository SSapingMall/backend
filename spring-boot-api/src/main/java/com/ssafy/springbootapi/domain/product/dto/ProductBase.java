package com.ssafy.springbootapi.domain.product.dto;

public class ProductBase {
    private String imageUrl;
    private String name;
    private int price;

    public ProductBase() {
    }

    public ProductBase(String imageUrl, String name, int price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
