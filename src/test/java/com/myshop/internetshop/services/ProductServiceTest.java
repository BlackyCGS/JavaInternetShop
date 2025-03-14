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

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private Product product;
    @BeforeEach
    void setUp() {
        product = new Product(1000, 1,"Gpu", 1200);
    }

    @Test
    void getAllProductsShouldReturnListOfProductsDtos() {
        when(productsRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductDto> result = productsService.getAllProducts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Gpu", result.get(0).getName());
    }
}
