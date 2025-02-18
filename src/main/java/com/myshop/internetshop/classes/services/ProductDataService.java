package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.ProductDataDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductDataService {
    ProductDataDto getProductData(int id);

    List<ProductDataDto> getProductDatas(int amount);
}
