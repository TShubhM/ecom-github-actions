package com.mongo.Ecom.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "category")
public class Category {
	@Id
	private String id;
	@Indexed
	private String categoryName;
	@Indexed
	private boolean categoryEnabled;
	@DBRef
	private List<Brand> brands;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public Category(String id, String categoryName, boolean categoryEnabled, List<Brand> brands) {
//		super();
//		this.id = id;
//		this.categoryName = categoryName;
//		this.categoryEnabled = categoryEnabled;
//		this.brands = brands;
//	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getCategoryName() {
//		return categoryName;
//	}
//
//	public void setCategoryName(String categoryName) {
//		this.categoryName = categoryName;
//	}
//
//	public boolean isCategoryEnabled() {
//		return categoryEnabled;
//	}
//
//	public void setCategoryEnabled(boolean categoryEnabled) {
//		this.categoryEnabled = categoryEnabled;
//	}
//
//	public List<Brand> getBrands() {
//		return brands;
//	}
//
//	public void setBrands(List<Brand> brands) {
//		this.brands = brands;
//	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", categoryEnabled=" + categoryEnabled
				+ ", brands=" + brands + "]";
	}

}
