package com.restoapp.restaurant_ordering_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.restoapp.restaurant_ordering_system.model.Order;
import java.util.List;


public interface OrderRepository extends MongoRepository<Order,String> {
  List<Order> findByUserId(String userId);
}
