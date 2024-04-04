package com.ssafy.springbootapi.domain.product.dto;

public class ProductOutput extends ProductListOutput{
    private String description;
    private int category;
    private int stock;
    private int user_id;

    public ProductOutput() {
        super();
    }

    public ProductOutput(String imageUrl, String name, int price, Long id, String description, int category, int stock, int user_id) {
        super(imageUrl, name, price, id);
        this.description = description;
        this.category = category;
        this.stock = stock;
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
