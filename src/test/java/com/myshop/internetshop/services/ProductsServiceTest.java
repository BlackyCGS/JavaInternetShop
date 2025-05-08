package com.myshop.internetshop.services;

import com.myshop.internetshop.classes.cache.Cache;
import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Motherboard;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.exceptions.ValidationException;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import com.myshop.internetshop.classes.services.ProductsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private Cache<Product> productCache;

    @InjectMocks
    private ProductsService productsService;

    private Product testProduct;
    private ProductDto testProductDto;
    private Gpu testGpu;
    private Motherboard testMotherboard;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setName("Test Product");
        testProduct.setPrice((float)100.0);

        testGpu = new Gpu();
        testGpu.setProductId(1);
        testGpu.setProducer("NVIDIA");
        testMotherboard = new Motherboard();
        testMotherboard.setProductId(1);
        testMotherboard.setSocket("AM4");

        testProductDto = new ProductDto();
        testProductDto.setName("Test Product");
        testProductDto.setPrice((float)100.0);

    }

    @Test
    void getAllProducts_Success() {
        // Arrange
        List<Product> products = List.of(testProduct);
        when(productsRepository.findAll()).thenReturn(products);

        // Act
        Pageable pageable = PageRequest.of(0, 10);
        List<ProductDto> result = productsService.getAllProducts(pageable);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
    }

    @Test
    void getAllProducts_Empty_ThrowsNotFoundException() {
        // Arrange
        when(productsRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        Pageable pageable = PageRequest.of(0, 10);
        assertThrows(NotFoundException.class, () -> productsService.getAllProducts(pageable));
    }

    @Test
    void getProductById_FromCache() {
        // Arrange
        String cacheKey = "products-1";
        when(productCache.contains(cacheKey)).thenReturn(true);
        when(productCache.get(cacheKey)).thenReturn(testProduct);

        // Act
        Product result = productsService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productsRepository, never()).findById(anyLong());
    }

    @Test
    void getProductById_FromRepository() {
        // Arrange
        String cacheKey = "products-1";
        when(productCache.contains(cacheKey)).thenReturn(false);
        when(productsRepository.existsById(1L)).thenReturn(true);
        when(productsRepository.findById(1L)).thenReturn(testProduct);

        // Act
        Product result = productsService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productCache).put(cacheKey, testProduct);
    }

    @Test
    void getProductById_NotFound_ThrowsNotFoundException() {
        // Arrange
        String cacheKey = "products-1";
        when(productCache.contains(cacheKey)).thenReturn(false);
        when(productsRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> productsService.getProductById(1L));
    }

    @Test
    void getGpuByParams_Success() {
        // Arrange
        List<Product> products = List.of(testProduct);
        Pageable pageable = PageRequest.of(0, 10);
        when(productsRepository.findGpuByParams(anyString(), anyInt(), anyInt(),
                anyInt(), anyInt(), anyInt(), pageable))
                .thenReturn(products);

        // Act
        List<ProductDto> result = productsService.getGpuByParams("NVIDIA", 1500, 3, 2,
                200, 8, pageable);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getGpuByParams_NotFound_ThrowsNotFoundException() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(productsRepository.findGpuByParams(anyString(), anyInt(), anyInt(),
                anyInt(), anyInt(), anyInt(), pageable))
                .thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(NotFoundException.class, () ->
                productsService.getGpuByParams("NVIDIA", 1500, 3, 2, 200, 8, pageable));
    }

    @Test
    void deleteProductById_Success() {
        // Arrange
        String cacheKey = "products-1";
        when(productsRepository.existsById(1L)).thenReturn(true);
        when(productCache.contains(cacheKey)).thenReturn(true);

        // Act
        productsService.deleteProductById(1L);

        // Assert
        verify(productCache).remove(cacheKey);
        verify(productsRepository).deleteById(1L);
    }

    @Test
    void deleteProductById_NotFound_ThrowsNotFoundException() {
        // Arrange
        when(productsRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> productsService.deleteProductById(1L));
        verify(productCache, never()).remove(anyString());
        verify(productsRepository, never()).deleteById(anyLong());
    }

    @Test
    @Transactional
    void saveProduct_WithGpu_Success() {
        // Arrange
        testProductDto.setGpu(testGpu);
        when(productsRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act
        ProductDto result = productsService.saveProduct(testProductDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productsRepository).save(any(Product.class));
    }

    @Test
    @Transactional
    void saveProduct_WithMotherboard_Success() {
        // Arrange
        testProductDto.setMotherboard(testMotherboard);
        when(productsRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act
        ProductDto result = productsService.saveProduct(testProductDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productsRepository).save(any(Product.class));
    }

    @Test
    void saveProduct_InvalidCategoryCount_ThrowsValidationException() {
        // Arrange
        testProductDto.setGpu(testGpu);
        testProductDto.setMotherboard(testMotherboard);

        // Act & Assert
        assertThrows(ValidationException.class, () -> productsService.saveProduct(testProductDto));
        verify(productsRepository, never()).save(any(Product.class));
    }

    @Test
    void saveProducts_Success() {
        // Arrange
        testProductDto.setGpu(testGpu);
        testProduct.setGpu(testGpu);
        List<ProductDto> productDtos = List.of(testProductDto);
        List<Product> products = List.of(testProduct);
        when(productsRepository.saveAll(anyList())).thenReturn(products);

        // Act
        List<ProductDto> result = productsService.saveProducts(productDtos);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productsRepository).saveAll(anyList());
    }

    @Test
    void existsById_ReturnsTrue() {
        // Arrange
        when(productsRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = productsService.existsById(1L);

        // Assert
        assertTrue(result);
    }

    @Test
    void existsById_ReturnsFalse() {
        // Arrange
        when(productsRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = productsService.existsById(1L);

        // Assert
        assertFalse(result);
    }

    @Test
    void clearCache_Success() {
        // Act
        productsService.clearCache();

        // Assert
        verify(productCache).clear();
    }

    @Test
    void deleteAll_Success() {
        // Act
        productsService.deleteAll();

        // Assert
        verify(productsRepository).deleteAll();
    }
}