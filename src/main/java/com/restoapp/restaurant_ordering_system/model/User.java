package com.restoapp.restaurant_ordering_system.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
  @Id
  private String id;
  private String username;
  private String password;
  private String email;
  private String role;   
}