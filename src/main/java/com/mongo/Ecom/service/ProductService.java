package com.mongo.Ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.Ecom.exception.EmptyInputException;
import com.mongo.Ecom.exception.NoSuchObjectExistException;
import com.mongo.Ecom.exception.ObjectNotFoundException;
import com.mongo.Ecom.model.Product;
import com.mongo.Ecom.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository prodRepo;

	public List<Product> findAll() {
		List<Product> list = prodRepo.findAll();
		if (list.isEmpty()) {
			throw new RuntimeException("The Product List you are requesting is empty.");
		} else {
			return list;
		}
	}

	public Product getProductById(String id) {
		Product product = prodRepo.findById(id).orElse(null);
		if (product != null) {
			return product;
		} else
			throw new ObjectNotFoundException("404", "The product with Id " + id + " is not present.");
	}

	public Product getProductByName(String name) {
		Product product = prodRepo.findByName(name).orElse(null);
		if (product != null) {
			return product;
		} else
			throw new ObjectNotFoundException("404", "The product with Name " + name + " is not present.");
	}

	public List<Product> getProductByBrandId(String id) {
		List<Product> list = prodRepo.findByBrandId(id);
		if (list.isEmpty()) {
			throw new RuntimeException("The product List you are requesting with brand Id " + id + " is empty.");
		} else {
			return list;
		}
	}

	public Product addProduct(Product product) {
		List<Product> productWithBrandList = prodRepo.findByBrandId(product.getBrandId());
		if (productWithBrandList.isEmpty()) {
			throw new NullPointerException("List you are requesting is empty");
		}
		if (product.getId().isEmpty() || product.getBrandId().isEmpty() || product.getDescription().isEmpty()
				|| product.getImage().isEmpty() || product.getName().isEmpty() || product.getPrice() == 0.0) {
			throw new EmptyInputException("204", "Input field is Empty, Please Check again.");
		}
		Product existingProduct = prodRepo.findById(product.getId()).orElse(null);
		if (existingProduct == null) {
			Product savedProduct = prodRepo.save(product);
			return savedProduct;
		} else
			throw new ObjectNotFoundException("400",
					"Product with Id " + product.getId() + " And Name " + product.getName() + " Already Exist.");

	}

	public Product updateProductById(Product product) {
		Product productFound = prodRepo.findById(product.getId()).orElse(null);
		if (productFound == null) {
			throw new NoSuchObjectExistException("204", "Product You are requesting is not present");
		} else {
			productFound.setBrandId(product.getBrandId());
			productFound.setDescription(product.getDescription());
			productFound.setImage(product.getImage());
			productFound.setName(product.getName());
			productFound.setPrice(product.getPrice());
			prodRepo.save(productFound);
			return productFound;
		}

	}

	public String deleteProductById(String id) {
		Product productFound = prodRepo.findById(id).orElse(null);
		if (productFound != null) {
			prodRepo.delete(productFound);
			return "Product Deleted Successfully";
		} else {
			throw new RuntimeException("Product you want to delete is not present. Please check again.");
		}
	}

	public String deleteProductByName(String name) {
		Product productFound = prodRepo.findByName(name).get();
		if (productFound != null) {
			prodRepo.delete(productFound);
			return "Product with Name " + name + " is now deleted successfully";
		}
		return "No Product Found";
//		else {
//			throw new NoSuchElementException("no product found");
//		}
	}

	public String deleteProductByBrandId(String brandId){
		List<Product> list = prodRepo.findByBrandId(brandId);
		if(list.isEmpty()) {
			throw new RuntimeException("The product List with brandId "+brandId+" is empty");
		}else {
			prodRepo.deleteAll(list);
			return "The Products with BrandID "+brandId+" removed successfully";
		}
	}
}
