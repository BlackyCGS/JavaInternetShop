package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Psu;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PsuDto {

    private Integer productId;

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "producer can not be blank")
    private String producer;

    @NotBlank(message = "watt can not be blank")
    private int watt;

    @NotBlank(message = "size can not be blank")
    private String size;

    @NotBlank(message = "efficiency rating can not be blank")
    private String efficiencyRating;

    public PsuDto(Psu psu) {
        this.productId = psu.getProductId();
        this.name = psu.getName();
        this.producer = psu.getProducer();
        this.watt = psu.getWatt();
        this.size = psu.getSize();
        this.efficiencyRating = psu.getEfficiencyRating();
    }

    public Psu toEntity() {
        Psu psu = new Psu();
        psu.setProductId(this.getProductId());
        psu.setName(this.getName());
        psu.setProducer(this.getProducer());
        psu.setWatt(this.getWatt());
        psu.setSize(this.getSize());
        psu.setEfficiencyRating(this.getEfficiencyRating());
        return psu;
    }
}
