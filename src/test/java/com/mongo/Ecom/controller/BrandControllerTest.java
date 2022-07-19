package com.mongo.Ecom.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.mongo.Ecom.model.Brand;
import com.mongo.Ecom.repository.BrandRepository;
import com.mongo.Ecom.service.BrandService;

@SpringBootTest(classes = BrandController.class)
@RunWith(MockitoJUnitRunner.class)
public class BrandControllerTest {
	@InjectMocks
	BrandController controller;

	@Mock
	BrandService service;

	@Mock
	BrandRepository repository;

	@Test
	public void getAllBrandsTest() {
		List<Brand> list = new ArrayList<>();
		Brand brand = new Brand("1", "Nike", "nike logo", "1", null);
		list.add(brand);
		when(service.findAll()).thenReturn(list);
		ResponseEntity<List<Brand>> res = new ResponseEntity<List<Brand>>(list, HttpStatus.OK);
		assertEquals(res, controller.getAllBrands());
	}

	@Test
	public void getBrandByIdTest() {
		Brand brandFound = new Brand("1", "Nike", "Nike logo", "1", null);
		when(service.getBrandById(anyString())).thenReturn(brandFound);
		ResponseEntity<Brand> res = new ResponseEntity<Brand>(brandFound, HttpStatus.FOUND);
		assertEquals(res, controller.getBrandById(anyString()));
	}

	@Test
	public void getBrandByNameTest() {
		Brand brandFound = new Brand("1", "Nike", "Nike logo", "1", null);
		when(service.getBrandByName(anyString())).thenReturn(brandFound);
		ResponseEntity<Brand> res = new ResponseEntity<Brand>(brandFound, HttpStatus.FOUND);
		assertEquals(res, controller.getBrandByName(anyString()));
	}

	@Test
	public void addBrandTest() {
		Brand brandFound = new Brand("1", "Nike", "Nike logo", "1", null);
		when(service.addBrand(brandFound)).thenReturn(brandFound);
		ResponseEntity<Brand> res = new ResponseEntity<Brand>(brandFound, HttpStatus.CREATED);
		assertEquals(res, controller.addBrand(brandFound));
	}

	@Test
	public void updateBrandTest() {
		Brand brandFound = new Brand("1", "Nike", "Nike logo", "1", null);
		when(service.updateBrandById(brandFound)).thenReturn(brandFound);
		ResponseEntity<Brand> res = new ResponseEntity<Brand>(brandFound, HttpStatus.OK);
		assertEquals(res, controller.updateBrand(brandFound));
	}

	@Test
	public void deleteBrandByIdTest() {
		when(service.deleteBrandById("1")).thenReturn("Brand with Id " + 1 + " is now deleted Successfully");
		ResponseEntity<String> res = new ResponseEntity<String>("Brand with Id " + 1 + " is now deleted Successfully",
				HttpStatus.OK);
		assertEquals(res, controller.deleteBrandById("1"));
	}
	
	@Test
	public void deleteBrandByNameTest() {
		when(service.deleteBrandByName("Nike")).thenReturn("Brand with " + "Nike" + " is now deleted successfully");
		ResponseEntity<String> res = new ResponseEntity<String>("Brand with " + "Nike" + " is now deleted successfully", HttpStatus.ACCEPTED);
		assertEquals(res, controller.deleteBrandByName("Nike"));
	}

}
