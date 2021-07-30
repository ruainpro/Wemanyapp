package com.dao.wethemany.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dao.wethemany.models.PurchasedProduct;
import com.dao.wethemany.models.Purchasing;

@Repository
public interface PurchasingInfoRepository extends MongoRepository<Purchasing, String>{

	List<Purchasing> findByUserEmail(String email);

}
