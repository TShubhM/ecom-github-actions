package com.mongo.Ecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.Ecom.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String>{

	Optional<Category> findByCategoryName(String categoryName);

	List<Category> findByCategoryEnabled(boolean categoryEnabled);


}
