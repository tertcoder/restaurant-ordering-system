package com.restoapp.restaurant_ordering_system.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;
import jakarta.annotation.PostConstruct;

@Configuration
public class FileStorageConfig {
    private Path fileStorageLocation;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            String filename = StringUtils.cleanPath(
                System.currentTimeMillis() + "_" + file.getOriginalFilename()
            );
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file", ex);
        }
    }
}
