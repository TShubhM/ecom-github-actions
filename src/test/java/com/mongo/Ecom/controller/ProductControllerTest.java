package com.mongo.Ecom.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mongo.Ecom.model.Product;
import com.mongo.Ecom.repository.ProductRepository;
import com.mongo.Ecom.service.ProductService;

@SpringBootTest(classes = ProductController.class)
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	@InjectMocks
	ProductController controller;

	@Mock
	ProductService service;

	@Mock
	ProductRepository repository;

	@Test
	public void getAllProductsTest() {
		Product product = new Product("1", "Mobile", 15000, "realme", "mobilephone", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		when(service.findAll()).thenReturn(list);
		ResponseEntity<List<Product>> res = new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		assertEquals(res, controller.getAllProducts());
	}

	@Test
	public void getProductByIdTest() {
		Product product = new Product("1", "Mobile", 15000, "realme", "mobilephone", "1");
		when(service.getProductById(product.getId())).thenReturn(product);
		ResponseEntity<Product> res = new ResponseEntity<Product>(product, HttpStatus.FOUND);
		assertEquals(res, controller.getProductById(product.getId()));
	}

	@Test
	public void getProductByNameTest() {
		Product product = new Product("1", "Mobile", 15000, "realme", "mobilephone", "1");
		when(service.getProductByName(product.getName())).thenReturn(product);
		ResponseEntity<Product> res = new ResponseEntity<Product>(product, HttpStatus.FOUND);
		assertEquals(res, controller.getProductByName(product.getName()));
	}

	@Test
	public void getProductsByBrandIdTest() {
		Product product = new Product("1", "Mobile", 15000, "realme", "mobilephone", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		when(service.getProductByBrandId(product.getBrandId())).thenReturn(list);
		ResponseEntity<List<Product>> res = new ResponseEntity<List<Product>>(list, HttpStatus.FOUND);
		assertEquals(res, controller.getProductsByBrandId(product.getBrandId()));
	}

	@Test
	public void addProductTest() {
		Product product = new Product("1", "Mobile", 15000, "realme", "mobilephone", "1");
		when(service.addProduct(product)).thenReturn(product);
		ResponseEntity<Product> res = new ResponseEntity<Product>(product, HttpStatus.CREATED);
		assertEquals(res, controller.addProduct(product));
	}

	@Test
	public void updateProductTest() {
		Product product = new Product("1", "Mobile", 15000, "realme", "mobilephone", "1");
		when(service.updateProductById(product)).thenReturn(product);
		ResponseEntity<Product> res = new ResponseEntity<Product>(product, HttpStatus.OK);
		assertEquals(res, controller.updateProduct(product));
	}

	@Test
	public void deleteProductByIdTest() {
		when(service.deleteProductById("1")).thenReturn("The Product with Id " + "1" + " is now deleted successfully.");
		ResponseEntity<String> res = new ResponseEntity<String>(
				"The Product with Id " + "1" + " is now deleted successfully.", HttpStatus.ACCEPTED);
		assertEquals(res, controller.deleteProductById("1"));
	}

	@Test
	public void deleteProductsByBrandIdTest() {
		when(service.deleteProductByBrandId("1")).thenReturn("The products with brandId " + "1" + " are removed.");
		ResponseEntity<String> res = new ResponseEntity<String>("The products with brandId " + "1" + " are removed.",
				HttpStatus.ACCEPTED);
		assertEquals(res, controller.deleteProductsByBrandId("1"));
	}

}
