package com.myshop.internetshop.classes.dto;

import static com.myshop.internetshop.classes.utilities.NumberParser.parseInteger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myshop.internetshop.classes.entities.Hdd;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class HddRequest {

    private String name;

    private String producer;

    private String size;

    public Hdd toEntity() {
        Hdd hdd = new Hdd();
        hdd.setName(name);
        hdd.setProducer(producer);
        hdd.setSize(parseInteger(size));
        return hdd;
    }
}
