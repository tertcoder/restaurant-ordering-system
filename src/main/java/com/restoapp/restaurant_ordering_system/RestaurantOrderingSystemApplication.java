 
package com.restoapp.restaurant_ordering_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class RestaurantOrderingSystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantOrderingSystemApplication.class);

    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantOrderingSystemApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logServerStartup() {
        logger.info("Server running on port 8080");
        
        if (mongoTemplate != null) {
            try {
                mongoTemplate.getDb().getName();
                logger.info("MongoDB connection successful");
            } catch (Exception e) {
                logger.error("MongoDB connection failed: {}", e.getMessage());
            }
        } else {
            logger.warn("MongoDB template not configured");
        }
    }
}