package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Ssd;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SsdDto {

    private Integer productId;

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "producer can not be blank")
    private String producer;

    @NotBlank(message = "form factor can not be blank")
    private String formFactor;

    @NotBlank(message = "protocol can not be blank")
    private String protocol;

    @NotBlank(message = "size can not be blank")
    private int size;

    public SsdDto(Ssd ssd) {
        this.productId = ssd.getProductId();
        this.name = ssd.getName();
        this.producer = ssd.getProducer();
        this.formFactor = ssd.getFormFactor();
        this.protocol = ssd.getProtocol();
        this.size = ssd.getSize();
    }

    public Ssd toEntity() {
        Ssd ssd = new Ssd();
        ssd.setProductId(productId);
        ssd.setName(name);
        ssd.setProducer(producer);
        ssd.setFormFactor(formFactor);
        ssd.setProtocol(protocol);
        ssd.setSize(size);
        return ssd;
    }

}
