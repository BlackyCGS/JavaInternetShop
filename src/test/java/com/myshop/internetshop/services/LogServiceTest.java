package com.myshop.internetshop.services;

import com.myshop.internetshop.classes.exceptions.BadRequestException;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.services.LogService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.AfterTestExecution;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @InjectMocks
    private LogService logService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String testDate = LocalDate.now().format(formatter);
    private final String testAdditional = "-test";

    private Path logFilePath;

    @BeforeEach
    void setUp() throws IOException {
        // Создаем временный лог-файл
        String fileName = "logs/internetShop" + testAdditional + "-" + testDate + ".log";
        logFilePath = Paths.get(fileName);
        Files.createDirectories(logFilePath.getParent());
        Files.write(logFilePath, "Log entry".getBytes(), StandardOpenOption.CREATE);
    }

    @AfterTestExecution
    void deleteTestFiles() throws IOException {
        Files.deleteIfExists(logFilePath);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetLogsByDate_FileExists() {
        // Act
        ResponseEntity<InputStreamResource> response =
                logService.getLogsByDate(testAdditional, testDate);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(
                "attachment; filename=internetShop" + testAdditional + "-" + testDate + ".log",
                response.getHeaders().getFirst("Content-Disposition")
        );
        assertNotNull(response.getBody());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetLogsByDate_FileNotFound() {
        // Act & Assert
        String nonExistentDate = "1999-01-01";
        LocalDate localDate = LocalDate.parse(nonExistentDate, formatter);
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> logService.getLogsByDate(testAdditional, nonExistentDate)
        );
        assertTrue(exception.getMessage().contains("There is no file with name " + testAdditional + "-" + formatter.format(localDate) +".log"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetLogsByDate_InvalidDateFormat() {
        // Act & Assert
        String invalidDate = "10-04-2025";
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> logService.getLogsByDate(testAdditional, invalidDate)
        );
        assertTrue(exception.getMessage().contains("Bad Request"));
    }
}
