package com.myshop.internetshop.classes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LogService {

    Logger logger = LoggerFactory.getLogger(LogService.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ResponseEntity<InputStreamResource> getLogsByDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            String fileName = "logs/internetShop-" + formatter.format(localDate) + ".log";
            Path path = Paths.get(fileName);

            if(!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            InputStreamResource resource =
                    new InputStreamResource(new FileInputStream(fileName));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName())
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        } catch (Exception e) {
            logger.warn("Error while getting logs from file: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
