package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    public final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        if (products.isEmpty()) {
            throw new NotFoundException("There are no products");
        }
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = convertToDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(product);
    }

    public Product getProductById(Integer productId) {
        return productsRepository.findById(productId);
    }

    public void deleteProductById(long productId) {
        if (productsRepository.existsById(productId)) {
            productsRepository.deleteById(productId);
        } else {
            throw new NotFoundException("There are already no product");
        }
    }

    public boolean existsById(long productId) {
        return productsRepository.existsById(productId);
    }

}
