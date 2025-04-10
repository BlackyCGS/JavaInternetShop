package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Motherboard;
import com.myshop.internetshop.classes.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Integer productId;
    @NotBlank(message = "Product name can not be blank")
    private String name;
    @NotNull(message = "Product price cannot be blank")
    private float price;

    private Gpu gpu;

    private Motherboard motherboard;

    public ProductDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.gpu = product.getGpu();
        this.motherboard = product.getMotherBoard();
    }

    public Product toEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setPrice(this.price);
        product.setGpu(this.gpu);
        this.gpu.setProductId(null);
        return product;

    }

}
