package com.mongo.Ecom.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.Ecom.controller.BrandController;
import com.mongo.Ecom.exception.BusinessException;
import com.mongo.Ecom.exception.EmptyInputException;
import com.mongo.Ecom.exception.ObjectNotFoundException;
import com.mongo.Ecom.model.Brand;
import com.mongo.Ecom.repository.BrandRepository;

@Service
public class BrandService {
	Logger log = LoggerFactory.getLogger(BrandController.class);
	
	@Autowired
	private CategoryService catser;
	
	@Autowired
	private BrandRepository brandRepo;

	public List<Brand> findAll() {
		List<Brand> list = brandRepo.findAll();
		if (list.isEmpty()) {
			log.error("The brand list you are requesting is empty");
			throw new RuntimeException("The Brand List you are requesting is empty.");
		} else {
			return list;
		}
	}

	public Brand getBrandById(String id) {
		Brand brandFound = brandRepo.findById(id).orElse(null);
		if (brandFound != null) {
			System.out.println(catser.findAllCategoryIds());
			
			return brandFound;
		} else {
			log.error("Given Id is not present in your application");
			throw new ObjectNotFoundException("404",
					"Brand with Given Id " + id + " is not present.please check again.");
		}
	}

	public Brand getBrandByName(String name) {
		Brand brandfound = brandRepo.findByName(name).orElse(null);
		if (brandfound != null) {
			return brandfound;
		} else
			throw new ObjectNotFoundException("404", "Brand With the Given name " + name + " is not present.");
	}

	public List<Brand> getBrandByCategoryId(String id) {
		List<Brand> brandList = brandRepo.findByCategoryId(id);
		if (brandList.isEmpty()) {
			throw new RuntimeException("The list you are requesting is empty");
		} else {
			return brandList;
		}

	}

	public Brand addBrand(Brand brand) {
		List<Brand> BrandListSameCategoryId = brandRepo.findByCategoryId(brand.getCategoryId());
		if (BrandListSameCategoryId.isEmpty()) {
			throw new BusinessException("400", "Bad Request. Category is not present for your brand");
		}
		if (brand.getId().isEmpty() || brand.getLogo().isEmpty() || brand.getCategoryId().isEmpty()
				|| brand.getName().isEmpty()) {
			throw new EmptyInputException("204", "Input Field is Empty, please check again.");
		}
		Brand existingBrand = brandRepo.findById(brand.getId()).orElse(null);
		if (existingBrand == null) {
			Brand savedBrand = brandRepo.save(brand);
			return savedBrand;
		} 
		else
			throw new ObjectNotFoundException("400",
					"Brand with Id " + brand.getId() + " And Name " + brand.getName() + " already exist");

	}

	public Brand updateBrandById(Brand brand) {
		Brand brandFound = brandRepo.findById(brand.getId()).orElse(null);
		if (brandFound != null) {
			brandFound.setCategoryId(brand.getCategoryId());
			brandFound.setName(brand.getName());
			brandFound.setLogo(brand.getLogo());
			brandRepo.save(brandFound);
			return brandFound;
		} else
			throw new NoSuchElementException("Brand with given Id " + brand.getId() + " is not present.");
	}

	public String deleteBrandById(String id) {
		Brand brandFound = brandRepo.findById(id).orElse(null);
		if (brandFound != null) {
			brandRepo.delete(brandFound);
			return "Brand deleted successfully";
		} else {
			log.error("Brand is not present in your application");
			throw new RuntimeException("Brand you want to delete is not present. please check again.");
		}
	}

	public String deleteBrandByName(String name) {
		Brand brandFound = brandRepo.findByName(name).orElse(null);
		if (brandFound != null) {
			brandRepo.delete(brandFound);
			return "Brand Deleted Successfully";
		} else {
			throw new ObjectNotFoundException("404", "Brand you want to delete is not present.");
		}
	}

}
