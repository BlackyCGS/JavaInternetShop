package com.myshop.internetshop.classes.services;

import org.springframework.stereotype.Service;
import com.myshop.internetshop.classes.dto.ProductDataDto;

import java.util.List;

@Service
public interface ProductDataService {
      ProductDataDto getProductData(int id);
      List<ProductDataDto> getProductDatas(int amount);
}
