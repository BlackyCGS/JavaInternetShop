package com.myshop.internetshop.services;

import static org.junit.jupiter.api.Assertions.*;
import com.myshop.internetshop.classes.dto.ProductDataDto;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.services.implementations.ProductDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class ProductDataServiceImplTest {

    @InjectMocks
    private ProductDataServiceImpl productDataService;

    @Test
    void getProductDataShouldReturnProductDataDto() {
        ProductDataDto result = productDataService.getProductData(1);
        assertNotNull(result);
        assertEquals("RTX 4080", result.getName());
        assertEquals(200, result.getPrice());
    }

    @Test
    void getProductDataShouldThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> productDataService.getProductData(10));
    }

    @Test
    void getProductDatasShouldReturnListOfProductDataDtos() {
        List<ProductDataDto> result = productDataService.getProductDatas(3);
        assertEquals(3, result.size());
        assertEquals("RTX 4090", result.get(0).getName());
    }

    @Test
    void getProductDataByIdShouldReturnProductDataDto() {
        ProductDataDto result = productDataService.getProductDataById(2);
        assertNotNull(result);
        assertEquals("RTX 3070TI", result.getName());
        assertEquals(1100, result.getPrice());
    }

    @Test
    void getProductDataByIdShouldThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> productDataService.getProductDataById(10));
    }
}

