package com.restoapp.restaurant_ordering_system.model;
  

import lombok.Data;

@Data
public class OrderItem{
  private String menuItemId;
  private String name;
  private int quantity;
  private int price;
}