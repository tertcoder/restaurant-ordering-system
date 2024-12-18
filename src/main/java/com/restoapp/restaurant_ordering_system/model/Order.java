package com.restoapp.restaurant_ordering_system.model;
 
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "orders")
public class Order {
  @Id
  private String id;
  private String userId;
  private List<OrderItem> items;
  private int totalPrice;
  private String status;
  private LocalDateTime createdAt;

}

