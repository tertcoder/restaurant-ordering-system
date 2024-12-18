package com.restoapp.restaurant_ordering_system.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.restoapp.restaurant_ordering_system.model.MenuItem; 

public interface MenuItemRepository extends MongoRepository<MenuItem,String> {
  List<MenuItem> findByAvailableTrue();
}
