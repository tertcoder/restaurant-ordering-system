package com.restoapp.restaurant_ordering_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restoapp.restaurant_ordering_system.model.Order;
import com.restoapp.restaurant_ordering_system.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
@Autowired
private OrderService orderService;

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
      return ResponseEntity.ok(orderService.createOrder(order));
  }
  @PatchMapping("/{orderId}/status")
  public ResponseEntity<Order> updateOrderStatus(
    @PathVariable String orderId,
    @RequestParam String status
  ){
    return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Order>> getUserOrders(@PathVariable String userId) {
      return ResponseEntity.ok(orderService.getUserOrders(userId));
  }


}
