package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.dto.MotherboardRequest;
import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Motherboard;
import com.myshop.internetshop.classes.entities.Product;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ParserService {

    private final Logger logger = LoggerFactory.getLogger(ParserService.class);

    public List<ProductDto> massGpuParser(List<GpuRequest> gpuRequests) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (GpuRequest gpuRequest : gpuRequests) {
            Product product = new Product();
            Gpu gpu = gpuRequest.toEntity();
            product.setName(gpu.getName());
            product.setGpu(gpu);
            productDtos.add(convertToDto(product));
        }
        logger.info("massGpuParser return");
        return productDtos;
    }

    public List<ProductDto> massMotherboardParser(List<MotherboardRequest> motherboards) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (MotherboardRequest motherboardRequest : motherboards) {
            Product product = new Product();
            Motherboard motherboard = motherboardRequest.toEntity();
            product.setName(motherboard.getName());
            product.setMotherBoard(motherboard);
            productDtos.add(convertToDto(product));
        }
        logger.info("massMotherboardParser return");
        return productDtos;
    }

    public ProductDto convertToDto(Product product) {
        return new ProductDto(product);
    }
}
