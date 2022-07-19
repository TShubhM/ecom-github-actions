package com.mongo.Ecom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.mongo.Ecom.model.Brand;
import com.mongo.Ecom.service.BrandService;

@RestController
@RequestMapping("brand")
public class BrandController {
	
	Logger log = LoggerFactory.getLogger(BrandController.class);
	
	
	@Autowired
	private BrandService brandService;

	@GetMapping("/all")
	public ResponseEntity<List<Brand>> getAllBrands() {
		log.info("getAllBrand Controller method is accessed");
		List<Brand> list = brandService.findAll();
		return new ResponseEntity<List<Brand>>(list, HttpStatus.OK);
	}

	@GetMapping("/getBrandById/{id}")
	public ResponseEntity<Brand> getBrandById(@PathVariable String id) {
		log.debug("Request for id {}",id);
		Brand brandFound = brandService.getBrandById(id);
		log.trace("Brand Id is Retrieved");
		return new ResponseEntity<Brand>(brandFound, HttpStatus.FOUND);
	}

	@GetMapping("/getBrandByName/{name}")
	public ResponseEntity<Brand> getBrandByName(@PathVariable String name) {
		Brand brandFound = brandService.getBrandByName(name);
		return new ResponseEntity<Brand>(brandFound, HttpStatus.FOUND);
	}

//	@GetMapping("/getBrandByCategoryId/{catId}")
//	public ResponseEntity<List<Brand>> getBrandByCategoryId(@PathVariable String catId) {
//		List<Brand> brandList = brandService.getBrandByCategoryId(catId);
//		return new ResponseEntity<List<Brand>>(brandList, HttpStatus.FOUND);
//	}

	@PostMapping("/save")
	public ResponseEntity<Brand> addBrand(@RequestBody Brand brand) {
		Brand brandFound = brandService.addBrand(brand);
		return new ResponseEntity<Brand>(brandFound, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Brand> updateBrand(@RequestBody Brand brand) {
		Brand brandFound = brandService.updateBrandById(brand);
		return new ResponseEntity<Brand>(brandFound, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBrandById(@PathVariable String id) {
		brandService.deleteBrandById(id);
		return new ResponseEntity<String>("Brand with Id " + id + " is now deleted Successfully", HttpStatus.OK);
	}

	@DeleteMapping("/deleteByName/{name}")
	public ResponseEntity<String> deleteBrandByName(@PathVariable String name) {
		brandService.deleteBrandByName(name);
		return new ResponseEntity<String>("Brand with " + name + " is now deleted successfully", HttpStatus.ACCEPTED);
	}

}
