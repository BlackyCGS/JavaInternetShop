package com.myshop.internetshop.classes.dto;

import static com.myshop.internetshop.classes.utilities.NumberParser.parseInteger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myshop.internetshop.classes.entities.Psu;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PsuRequest {

    private String name;

    private String producer;

    private String watt;

    private String size;

    private String efficiencyRating;

    public Psu toEntity() {
        Psu psu = new Psu();
        psu.setName(name);
        psu.setProducer(producer);
        psu.setWatt(parseInteger(watt));
        psu.setSize(size);
        psu.setEfficiencyRating(efficiencyRating);
        return psu;
    }
}
