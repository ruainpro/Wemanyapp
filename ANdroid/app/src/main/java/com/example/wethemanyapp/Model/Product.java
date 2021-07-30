package com.example.wethemanyapp.Model;

public class Product {

    private String id;

    private String name;

    private String description;

    private double price;

    private double co2Emission ;

    private String images;

    private String category;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", co2Emission=" + co2Emission +
                ", images='" + images + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCo2Emission() {
        return co2Emission;
    }

    public void setCo2Emission(double co2Emission) {
        this.co2Emission = co2Emission;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Product(String id, String name, String description, double price, double co2Emission, String images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.co2Emission = co2Emission;
        this.images = images;
    }
    public Product() {

    }





}
