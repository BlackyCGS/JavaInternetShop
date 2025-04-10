package com.myshop.internetshop.services;

import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.services.LogService;
import org.junit.jupiter.api.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LogServiceTest {

    private LogService logService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String testDate = LocalDate.now().format(formatter);
    private final String testAdditional = "-test";

    @BeforeEach
    void setUp() throws IOException {
        logService = new LogService();

        // Создаем временный лог-файл
        String fileName = "logs/internetShop" + testAdditional + "-" + testDate + ".log";
        Path logPath = Paths.get(fileName);
        Files.createDirectories(logPath.getParent());
        Files.write(logPath, "Log entry".getBytes(), StandardOpenOption.CREATE);
        Path logPath2 = Paths.get(fileName);
        Files.createDirectories(logPath2.getParent());
        Files.write(logPath2, "Log entry".getBytes(), StandardOpenOption.CREATE);
    }

    @AfterEach
    void tearDown() throws IOException {
        String fileName2 = "logs/internetShop" + testAdditional + "-" + testDate + "2" +
                ".log";
        Files.deleteIfExists(Paths.get(fileName2));
    }

    @Test
    void testGetLogsByDate_FileExists() {
        ResponseEntity<InputStreamResource> response =
                logService.getLogsByDate(testAdditional, testDate);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("attachment; filename=internetShop" + testAdditional + "-" + testDate + ".log",
                response.getHeaders().getFirst("Content-Disposition"));
        assertNotNull(response.getBody());
    }

    @Test
    void testGetLogsByDate_FileNotFound() {
        String nonExistentDate = "1999-01-01";
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> logService.getLogsByDate(testAdditional, nonExistentDate)
        );
        assertTrue(exception.getMessage().contains("There is no file with name"));
    }

    @Test
    void testGetLogsByDate_InvalidDateFormat() {
        String invalidDate = "10-04-2025";
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> logService.getLogsByDate(testAdditional, invalidDate)
        );
        assertTrue(exception.getMessage().contains("Error while getting logs from file"));
    }
}
