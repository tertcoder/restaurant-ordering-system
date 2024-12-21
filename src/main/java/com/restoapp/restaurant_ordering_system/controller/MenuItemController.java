package com.restoapp.restaurant_ordering_system.controller;
 
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restoapp.restaurant_ordering_system.config.FileStorageConfig;
import com.restoapp.restaurant_ordering_system.model.MenuItem;
import com.restoapp.restaurant_ordering_system.service.MenuItemService;

@RestController
@RequestMapping("/api/menu")
public class MenuItemController {
  @Autowired
  private MenuItemService menuItemService;

  @Autowired
  private FileStorageConfig fileStorageConfig;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MenuItem> createMenuItem(
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam("category") String category,
        @RequestParam("image") MultipartFile image
    ) {
        String imageUrl = fileStorageConfig.storeFile(image);
        
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setCategory(category);
        menuItem.setImageUrl("/uploads/" + imageUrl);
        menuItem.setAvailable(true);

        return ResponseEntity.ok(menuItemService.createMenuItem(menuItem));
    }

  // @PostMapping
  // public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
  //     return ResponseEntity.ok(menuItemService.createMenuItem(menuItem));
  // }

  @GetMapping
  public ResponseEntity<List<MenuItem>> getAvailableMenuItems() {
    System.out.println("OK");

      return ResponseEntity.ok(menuItemService.getAvailableMenuItems());
  }

  

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMenuItem(@PathVariable String id) {
      menuItemService.deleteMenuItem(id);
      return ResponseEntity.ok().build();
  }

@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<MenuItem> updateMenuItem(
    @PathVariable String id,
    @RequestParam("name") String name,
    @RequestParam("price") int price,
    @RequestParam("category") String category,
    @RequestParam(value = "image", required = false) MultipartFile image
) {
    MenuItem existingItem = menuItemService.getMenuItem(id);
    existingItem.setName(name);
    existingItem.setPrice(price);
    existingItem.setCategory(category);
    
    if (image != null) {
        String imageUrl = fileStorageConfig.storeFile(image);
        existingItem.setImageUrl("/uploads/" + imageUrl);
    }
    
    return ResponseEntity.ok(menuItemService.updateMenuItem(id, existingItem));
}
}
