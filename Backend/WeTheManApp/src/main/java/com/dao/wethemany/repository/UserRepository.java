package com.dao.wethemany.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dao.wethemany.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);

//  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
