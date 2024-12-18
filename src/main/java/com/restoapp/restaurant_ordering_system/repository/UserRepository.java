package com.restoapp.restaurant_ordering_system.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.restoapp.restaurant_ordering_system.model.User;

public interface UserRepository extends MongoRepository<User,String> {
  Optional<User> findByUsername(String username);
  
}
