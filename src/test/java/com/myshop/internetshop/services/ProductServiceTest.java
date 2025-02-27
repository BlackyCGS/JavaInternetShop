package com.myshop.internetshop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.dto.ProductsDto;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import com.myshop.internetshop.classes.services.ProductsService;
import com.myshop.internetshop.classes.entities.Products;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private Products product;
    @BeforeEach
    void setUp() {
        product = new Products(1000, 1,"Gpu", 1200);
    }

    @Test
    void getAllProductsShouldReturnListOfProductsDtos() {
        when(productsRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductsDto> result = productsService.getAllProducts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Gpu", result.get(0).getName());
    }
}
