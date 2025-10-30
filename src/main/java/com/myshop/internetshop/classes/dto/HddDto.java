package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Hdd;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class HddDto {

    private Integer productId;

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "producer can not be blank")
    private String producer;

    @NotBlank(message = "size can not be blank")
    private int size;

    public HddDto(Hdd hdd) {
        this.productId = hdd.getProductId();
        this.name = hdd.getName();
        this.producer = hdd.getProducer();
        this.size = hdd.getSize();
    }

    public Hdd toEntity() {
        Hdd hdd = new Hdd();
        hdd.setProductId(productId);
        hdd.setName(name);
        hdd.setProducer(producer);
        hdd.setSize(size);
        return hdd;
    }
}
