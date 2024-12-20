package com.restoapp.restaurant_ordering_system.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restoapp.restaurant_ordering_system.model.MenuItem;
import com.restoapp.restaurant_ordering_system.model.Order;
import com.restoapp.restaurant_ordering_system.model.OrderItem;
import com.restoapp.restaurant_ordering_system.repository.MenuItemRepository;
import com.restoapp.restaurant_ordering_system.repository.OrderRepository;

@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private MenuItemRepository menuItemRepository;

  public Order createOrder(Order order) {
    int totalPrice = order.getItems()
    .stream()
    .map(item -> { 
        MenuItem menuItem = menuItemRepository.findById(item.getMenuItemId())
            .orElseThrow(() -> new RuntimeException("Menu Item not found"));
      
        return BigDecimal.valueOf(menuItem.getPrice())
                .multiply(BigDecimal.valueOf(item.getQuantity()));
    })
    .reduce(BigDecimal.ZERO, BigDecimal::add)  
    .intValue();
    
    order.setTotalPrice(totalPrice); 
    order.setCreatedAt(LocalDateTime.now());
    order.setStatus("PENDING");

    return orderRepository.save(order); 
  }

  public Order updateOrderStatus(String orderId, String status){
    Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

    order.setStatus(status);
    return orderRepository.save(order); 
  }

  public List<Order> getUserOrders(String userId) {
    return orderRepository.findByUserId(userId);
  }
  public List<Order> getAllOrders(){
    return orderRepository.findAll();
  }

  public Order updateOrderItems(String orderId, List<OrderItem> newItems) {
    // Find the order
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

    // If items list is empty, delete the order and return null
    if (newItems == null || newItems.isEmpty()) {
        orderRepository.deleteById(orderId);
        return null;
    }

    // Calculate new total price
    int totalPrice = newItems.stream()
        .map(item -> {
            MenuItem menuItem = menuItemRepository.findById(item.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu Item not found"));
            
            return BigDecimal.valueOf(menuItem.getPrice())
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .intValue();

    // Update order
    order.setItems(newItems);
    order.setTotalPrice(totalPrice);

    // Save and return
    return orderRepository.save(order);
}
}
