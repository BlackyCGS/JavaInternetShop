package com.myshop.internetshop.classes.services;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ResponseEntity<InputStreamResource> getLogsByDate(String additional,
                                                             String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            String fileName =
                    "logs/internetShop" + additional + "-" + formatter.format(localDate) +
                            ".log";
            Path path = Paths.get(fileName);

            if (!Files.exists(path)) {
                throw new NotFoundException("There is no file with name" + fileName);
            }

            InputStreamResource resource =
                    new InputStreamResource(new FileInputStream(fileName));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + path.getFileName())
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
