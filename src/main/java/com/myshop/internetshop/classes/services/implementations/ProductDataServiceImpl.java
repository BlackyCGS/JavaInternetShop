package com.myshop.internetshop.classes.services.implementations;

import com.myshop.internetshop.classes.dto.ProductDataDto;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.services.ProductDataService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class ProductDataServiceImpl implements ProductDataService {
    private final int[] prices = {500, 200, 100, 390};
    private final String[] names = {"RTX 4090", "RTX 4080", "RTX 4070", "RTX 4060"};
    private final int[] prices2 = {1500, 1200, 1100, 1390};
    private final String[] names2 = {"RTX 3080", "RTX 3070", "RTX 3070TI", "RTX 3060"};

    public ProductDataDto getProductData(int id) {
        if (id > prices.length - 1) {
            throw new NotFoundException("Not found");
        }
        return new ProductDataDto(prices[id], names[id]);
    }

    public List<ProductDataDto> getProductDatas(int amount) {
        List<ProductDataDto> productDatas = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ProductDataDto productDataDto = getProductData(i);
            if (productDataDto != null) {
                productDatas.add(productDataDto);
            }
        }
        return productDatas;
    }

    public ProductDataDto getProductDataById(int id) {
        if (id > prices.length - 1) {
            throw new NotFoundException("Not found");
        }
        return new ProductDataDto(prices2[id], names2[id]);
    }
}
