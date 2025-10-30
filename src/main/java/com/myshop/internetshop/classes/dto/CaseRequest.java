package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myshop.internetshop.classes.entities.PcCase;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CaseRequest {

    @JsonProperty("name")
    String name;

    @JsonProperty("producer")
    private String producer;

    @JsonProperty("motherboard")
    private String motherboard;

    @JsonProperty("powerSupply")
    private String powerSupply;

    public PcCase toEntity() {
        PcCase pcCaseEntity = new PcCase();
        pcCaseEntity.setName(name);
        pcCaseEntity.setProducer(producer);
        pcCaseEntity.setMotherboard(motherboard);
        pcCaseEntity.setPowerSupply(powerSupply);
        return pcCaseEntity;
    }
}
