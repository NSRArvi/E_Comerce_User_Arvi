package com.example.e_comerce_user.models;

public class ProductModel {
    private String productId;
    private String productName;
    private String category;
    private double price;
    private String productImageUrl;
    private String description;
    private boolean status;


    public ProductModel() {
    }

    public ProductModel(String productId, String productName, String category, double price, String productImageUrl, String description, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.productImageUrl = productImageUrl;
        this.description = description;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", productImageUrl='" + productImageUrl + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
