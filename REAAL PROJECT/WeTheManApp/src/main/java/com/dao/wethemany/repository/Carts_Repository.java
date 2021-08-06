package com.dao.wethemany.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dao.wethemany.models.Carts;


@Repository
public interface Carts_Repository extends MongoRepository<Carts, String>{

	List<Carts> findByCartedBy(String email);

	boolean findByIdAndCartedBy(String id, String email);

	void deleteByIdAndCartedBy(String id, String email);

	List<Carts> findByCartedByAndCartsStatus(String email, String string);
	
	

}
