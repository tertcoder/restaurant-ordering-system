  package com.restoapp.restaurant_ordering_system.model;

  import org.springframework.data.annotation.Id;
  import org.springframework.data.mongodb.core.mapping.Document;

  import lombok.Data;

  @Data
  @Document(collection = "menu_items")
  public class MenuItem {
    @Id
    private String id;
    private String name;
    private int price;
    private String category;
    private boolean available;
    private String imageUrl;
  }
