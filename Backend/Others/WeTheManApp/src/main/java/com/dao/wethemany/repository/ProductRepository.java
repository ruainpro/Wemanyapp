package com.dao.wethemany.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dao.wethemany.models.Product;
import com.dao.wethemany.models.User;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

}
