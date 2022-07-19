package com.mongo.Ecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.Ecom.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

	List<Product> findByBrandId(String brandId);

	Optional<Product> findByName(String name);


}
