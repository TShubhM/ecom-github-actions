package com.mongo.Ecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.Ecom.model.Brand;

public interface BrandRepository extends MongoRepository<Brand, String>{

	List<Brand> findByCategoryId(String categoryId);

	Optional<Brand> findByName(String name);

}
