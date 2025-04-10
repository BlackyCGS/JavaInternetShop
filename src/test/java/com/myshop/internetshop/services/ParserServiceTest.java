package com.myshop.internetshop.services;

import com.myshop.internetshop.classes.dto.*;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.services.ParserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ParserServiceTest {

    private final ParserService parserService = new ParserService();

    @Test
    void massGpuParser_Success() {
        // Arrange
        GpuRequest gpuRequest = new GpuRequest();
        gpuRequest.setBaseInfo("RTX 4090", "NVIDIA", "2520", "24", "1599.99");
        gpuRequest.setAdditionalInfo(3, 1, 2, "450", 0);

        List<GpuRequest> gpuRequests = List.of(gpuRequest);

        // Act
        List<ProductDto> result = parserService.massGpuParser(gpuRequests);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("RTX 4090", result.get(0).getName());
        assertNotNull(result.get(0).getGpu());
        assertEquals("NVIDIA", result.get(0).getGpu().getProducer());
    }

    @Test
    void massMotherboardParser_Success() {
        // Arrange
        MotherboardRequest motherboardRequest = new MotherboardRequest();
        motherboardRequest.setName("ROG STRIX Z790-E");
        motherboardRequest.setPrice("499.99");
        motherboardRequest.setProducer("ASUS");
        motherboardRequest.setSocket("LGA1700");

        List<MotherboardRequest> motherboardRequests = List.of(motherboardRequest);

        // Act
        List<ProductDto> result = parserService.massMotherboardParser(motherboardRequests);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("ROG STRIX Z790-E", result.get(0).getName());
        assertNotNull(result.get(0).getMotherboard());
        assertEquals("ASUS", result.get(0).getMotherboard().getProducer());
    }

    @Test
    void convertToDto_Success() {
        // Arrange
        Product product = new Product();
        product.setName("Test Product");

        // Act
        ProductDto result = parserService.convertToDto(product);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }
}