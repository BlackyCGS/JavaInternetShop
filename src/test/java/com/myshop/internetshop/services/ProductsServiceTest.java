package com.myshop.internetshop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import com.myshop.internetshop.classes.services.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;

import com.myshop.internetshop.classes.exceptions.NotFoundException;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setPrice(100);
    }

    @Test
    void testGetAllProducts_Success() {
        when(productsRepository.findAll()).thenReturn(Arrays.asList(product));
        List<ProductDto> products = productsService.getAllProducts();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
    }

    @Test
    void testGetAllProducts_ThrowsNotFoundException() {
        when(productsRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> productsService.getAllProducts());
    }

    @Test
    void testGetProductById_Success() {
        when(productsRepository.findById(1)).thenReturn(product);
        Product foundProduct = productsService.getProductById(1);
        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
    }

    @Test
    void testDeleteProductById_Success() {
        when(productsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productsRepository).deleteById(1L);
        assertDoesNotThrow(() -> productsService.deleteProductById(1L));
    }

    @Test
    void testDeleteProductById_ThrowsNotFoundException() {
        when(productsRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> productsService.deleteProductById(1L));
    }

    @Test
    void testExistsById() {
        when(productsRepository.existsById(1L)).thenReturn(true);
        assertTrue(productsService.existsById(1L));
    }
}
