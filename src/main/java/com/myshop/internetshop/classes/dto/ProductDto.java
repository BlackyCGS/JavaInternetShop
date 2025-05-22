package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.entities.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private Integer id;
    @NotBlank(message = "Product name can not be blank")
    private String name;
    @Min(value = 0, message = "Price must be more than 0")
    private float price;

    private GpuDto gpu;

    private MotherboardDto motherboard;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        if(product.getGpu() != null) {
            this.gpu = new GpuDto(product.getGpu());
        } else {
            this.gpu = null;
        }
        if(product.getMotherBoard() != null) {
            this.motherboard = new MotherboardDto(product.getMotherBoard());
        } else {
            this.motherboard = null;
        }
        }

    public Product toEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setPrice(this.price);
        product.setGpu(this.gpu.toEntity());
        product.setMotherBoard(this.motherboard.toEntity());
        if (product.getGpu() != null) {
            this.gpu.setProductId(null);
        }
        if (product.getMotherBoard() != null) {
            this.motherboard.setProductId(null);
        }
        return product;

    }

}
