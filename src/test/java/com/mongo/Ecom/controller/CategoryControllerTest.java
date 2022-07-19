package com.mongo.Ecom.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mongo.Ecom.model.Category;
import com.mongo.Ecom.repository.CategoryRepository;
import com.mongo.Ecom.service.CategoryService;

@SpringBootTest(classes = CategoryController.class)
@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
	@InjectMocks
	CategoryController controller;

	@Mock
	CategoryService service;

	@Mock
	CategoryRepository repository;

	@Test
	public void getAllCategoriesTest() {
		List<Category> list = new ArrayList<>();
		Category cat = new Category("1", "Footware", false, null);
		Category cat1 = new Category("2", "Footware", false, null);
		list.add(cat);
		list.add(cat1);
		when(service.findAll()).thenReturn(list);
		ResponseEntity<List<Category>> res = new ResponseEntity<List<Category>>(list, HttpStatus.OK);
		assertEquals(res, controller.getAllCategories());
	}

	@Test
	public void getCategoryByIdTest() {
		Category cat1 = new Category("2", "Footware", false, null);
		List<Category> list = new ArrayList<>();
		list.add(cat1);
		Optional<Category> optional = list.stream().findFirst();
		when(service.getCategoryById(anyString())).thenReturn(optional);
		ResponseEntity<Category> res = new ResponseEntity<Category>(optional.get(), HttpStatus.OK);
		assertEquals(res, controller.getCategoryById(anyString()));
	}

	@Test
	public void getCategoryByNameTest() {
		Category cat1 = new Category("2", "Footware", false, null);
		when(service.getCategoryByCategoryName(anyString())).thenReturn(cat1);
		ResponseEntity<Category> res = new ResponseEntity<Category>(cat1, HttpStatus.FOUND);
		assertEquals(res, controller.getCategoryByName(anyString()));
	}

	@Test
	public void getAllByCategoryEnabledTest() {
		List<Category> list = new ArrayList<>();
		Category cat1 = new Category("2", "Footware", false, null);
		list.add(cat1);
		when(service.getAllCategoryByCategoryEnabled(false)).thenReturn(list);
		ResponseEntity<List<Category>> res = new ResponseEntity<List<Category>>(list, HttpStatus.FOUND);
		assertEquals(res, controller.getAllByCategoryEnabled(false));
	}

	@Test
	public void addCategoryTest() {
		Category cat1 = new Category("2", "Footware", false, null);
		when(service.addCategory(cat1)).thenReturn(cat1);
		ResponseEntity<Category> res = new ResponseEntity<Category>(cat1, HttpStatus.CREATED);
		assertEquals(res, controller.addCategory(cat1));
	}

	@Test
	public void updateCategoryTest() {
		Category cat1 = new Category("2", "Footware", false, null);
		when(service.updateCategoryById(cat1)).thenReturn(cat1);
		ResponseEntity<Category> res = new ResponseEntity<Category>(cat1, HttpStatus.OK);
		assertEquals(res, controller.updateCategory(cat1));
	}

	@Test
	public void deleteCategoryTest() {
		when(service.deleteCategoryById(anyString())).thenReturn("Category Deleted Successfully");
		ResponseEntity<String> res = new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.OK);
		assertEquals(res, controller.deleteCategory(anyString()));
	}

	@Test
	public void deleteCategoryByCategoryNameTest() {
		when(service.deleteCategoryByName(anyString())).thenReturn("Category Deleted Successfully");
		ResponseEntity<String> res = new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.ACCEPTED);
		assertEquals(res, controller.deleteCategoryByCategoryName(anyString()));
	}

	@Test
	public void deleteCategoryByCategoryEnabledTest() {
		when(service.deleteCategoryByCategoryEnabled(false)).thenReturn("categories deleted successfully");
		ResponseEntity<String> res = new ResponseEntity<String>("categories deleted successfully", HttpStatus.ACCEPTED);
		assertEquals(res, controller.deleteCategoryByCategoryEnabled(false));
	}

}
