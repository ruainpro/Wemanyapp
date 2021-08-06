package com.dao.wethemany.models;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;


public class Product {

	@Id
	private String id;

	@NotBlank
    private String name;

	private String category;
	
	public Product(String id, @NotBlank String name, String category, @NotBlank String description,
			@NotBlank double price, @NotBlank double co2Emission, String images) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.co2Emission = co2Emission;
		this.images = images;
	}
	
	public Product() {

	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", description=" + description
				+ ", price=" + price + ", weight=" + weight + ", co2Emission=" + co2Emission + ", images=" + images
				+ "]";
	}

	@NotBlank
    private String description;

	@NotBlank
    private double price;

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	private double weight;
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@NotBlank
    private double co2Emission ;

//	@NotBlank
    private String images;

 
    
}
