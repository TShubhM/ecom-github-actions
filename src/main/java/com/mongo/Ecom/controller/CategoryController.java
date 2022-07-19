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

import com.mongo.Ecom.model.Category;
import com.mongo.Ecom.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService catService;

	@GetMapping("/all")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> list = catService.findAll();
		return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
	}

	@GetMapping("/getCategoryById/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
		Category catRetrieved = catService.getCategoryById(id).get();
		return new ResponseEntity<Category>(catRetrieved, HttpStatus.OK);
	}

	@GetMapping("/getCategoryByCategoryName/{name}")
	public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
		Category catRetrieved = catService.getCategoryByCategoryName(name);
		return new ResponseEntity<Category>(catRetrieved, HttpStatus.FOUND);
	}

	@GetMapping("/getByCategoryEnabled/{value}")
	public ResponseEntity<List<Category>> getAllByCategoryEnabled(@PathVariable boolean value) {
		List<Category> list = catService.getAllCategoryByCategoryEnabled(value);
		return new ResponseEntity<List<Category>>(list, HttpStatus.FOUND);
	}

	@PostMapping("/save")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		Category catFound = catService.addCategory(category);
		return new ResponseEntity<Category>(catFound, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
		Category catFound = catService.updateCategoryById(category);
		return new ResponseEntity<Category>(catFound, HttpStatus.OK);
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable String id) {
		catService.deleteCategoryById(id);
		return new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.OK);
	}

	@DeleteMapping("/deleteByName/{name}")
	public ResponseEntity<String> deleteCategoryByCategoryName(@PathVariable String name) {
		catService.deleteCategoryByName(name);
		return new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteByCatEnabled/{catEnable}")
	public ResponseEntity<String> deleteCategoryByCategoryEnabled(@PathVariable boolean catEnable) {
		catService.deleteCategoryByCategoryEnabled(catEnable);
		return new ResponseEntity<String>("categories deleted successfully", HttpStatus.ACCEPTED);
	}

}
