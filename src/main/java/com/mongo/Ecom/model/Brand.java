package com.mongo.Ecom.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "brand")
public class Brand {
	@Id
	private String id;
	private String name;
	private String logo;
	private String categoryId;
	@DBRef
	private List<Product> products;

	public Brand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Brand(String id, String name, String logo, String categoryId, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.categoryId = categoryId;
		this.products = products;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", logo=" + logo + ", categoryId=" + categoryId + "]";
	}

}
