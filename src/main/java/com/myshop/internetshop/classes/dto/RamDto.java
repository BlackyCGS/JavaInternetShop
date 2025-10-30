package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Ram;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RamDto {
    private Integer productId;

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "producer can not be blank")
    private String producer;

    @NotBlank(message = "ramType can not be blank")
    private String ramType;

    @NotBlank(message = "size can not be blank")
    private int size;

    @NotBlank(message = "timings can not be blank")
    private String timings;

    public RamDto(Ram ram) {
        this.productId = ram.getProductId();
        this.name = ram.getName();
        this.producer = ram.getProducer();
        this.ramType = ram.getRamType();
        this.size = ram.getSize();
        this.timings = ram.getTimings();
    }

    public Ram toEntity() {
        Ram ram = new Ram();
        ram.setProductId(productId);
        ram.setName(name);
        ram.setProducer(producer);
        ram.setRamType(ramType);
        ram.setSize(size);
        ram.setTimings(timings);
        return ram;
    }

}
