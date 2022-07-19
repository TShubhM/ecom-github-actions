package com.mongo.Ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.Ecom.model.Product;
import com.mongo.Ecom.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService prodService;

	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> list = prodService.findAll();
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}

	@GetMapping("/ProductById/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable String id) {
		Product product = prodService.getProductById(id);
		return new ResponseEntity<Product>(product, HttpStatus.FOUND);
	}

	@GetMapping("/ProductByName/{name}")
	public ResponseEntity<Product> getProductByName(@PathVariable String name) {
		Product product = prodService.getProductByName(name);
		return new ResponseEntity<Product>(product, HttpStatus.FOUND);
	}

	@GetMapping("/ProductsByBrandId/{id}")
	public ResponseEntity<List<Product>> getProductsByBrandId(@PathVariable String id) {
		List<Product> list = prodService.getProductByBrandId(id);
		return new ResponseEntity<List<Product>>(list, HttpStatus.FOUND);
	}

	@PostMapping("/save")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product productFound = prodService.addProduct(product);
		return new ResponseEntity<Product>(productFound, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		Product productUpdated = prodService.updateProductById(product);
		return new ResponseEntity<Product>(productUpdated, HttpStatus.OK);
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable String id) {
		prodService.deleteProductById(id);
		return new ResponseEntity<String>("The Product with Id " + id + " is now deleted successfully.",
				HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteProductByBrandId/{brandId}")
	public ResponseEntity<String> deleteProductsByBrandId(@PathVariable String brandId) {
		prodService.deleteProductByBrandId(brandId);
		return new ResponseEntity<String>("The products with brandId " + brandId + " are removed.",
				HttpStatus.ACCEPTED);
	}
}
