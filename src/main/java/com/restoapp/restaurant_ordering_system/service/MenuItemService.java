package com.restoapp.restaurant_ordering_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restoapp.restaurant_ordering_system.model.MenuItem;
import com.restoapp.restaurant_ordering_system.repository.MenuItemRepository;

@Service
public class MenuItemService {
  @Autowired
  private MenuItemRepository menuItemRepository;
  public MenuItem createMenuItem(MenuItem menuItem) {
    menuItem.setAvailable(true);
    return menuItemRepository.save(menuItem);
  }
  public List<MenuItem> getAvailableMenuItems() {
    return menuItemRepository.findByAvailableTrue();
  }
  public MenuItem updateMenuItem(String id,MenuItem updatedItem){
    MenuItem existingItem=menuItemRepository.findById(id).orElseThrow(()->new RuntimeException("Menu Item not found"));

    existingItem.setName(updatedItem.getName());
    existingItem.setPrice(updatedItem.getPrice());
    existingItem.setAvailable(updatedItem.isAvailable());
    existingItem.setCategory(updatedItem.getCategory());

    return menuItemRepository.save(existingItem);
  }

  public void deleteMenuItem(String id) {
    menuItemRepository.deleteById(id);
}

public MenuItem getMenuItem(String id) {
    return menuItemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Menu Item not found"));
}
}
