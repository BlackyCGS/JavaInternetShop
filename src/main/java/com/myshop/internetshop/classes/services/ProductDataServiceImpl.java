package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.ProductDataDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class ProductDataServiceImpl implements ProductDataService {
    private final int[] prices = {500, 200, 100, 390};
    private final String[] names = {"RTX 4090", "RTX 4080", "RTX 4070", "RTX 4060"};

    public ProductDataDto getProductData(int id) {
        if (id > prices.length - 1) {
            return new ProductDataDto(-1, "There is no product with id " + id);
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
}
