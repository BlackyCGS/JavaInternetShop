package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.PcCase;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PcCaseDto {
    private Integer productId;

    @NotBlank(message = "name can not be blank")
    String name;

    @NotBlank(message = "producer can not be blank")
    private String producer;

    @NotBlank(message = "motherboard type can not be blank")
    private String motherboard;

    @NotBlank(message = "powerSupply can not be blank")
    private String powerSupply;

    PcCaseDto(PcCase pcCase) {
        this.productId = pcCase.getProductId();
        this.name = pcCase.getName();
        this.producer = pcCase.getProducer();
        this.motherboard = pcCase.getMotherboard();
        this.powerSupply = pcCase.getPowerSupply();
    }

    public PcCase toEntity() {
        PcCase pcCase = new PcCase();
        pcCase.setProductId(productId);
        pcCase.setName(name);
        pcCase.setProducer(producer);
        pcCase.setMotherboard(motherboard);
        pcCase.setPowerSupply(powerSupply);
        return pcCase;
    }

}
