package com.myshop.internetshop.classes.dto;

import static com.myshop.internetshop.classes.utilities.NumberParser.parseInteger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myshop.internetshop.classes.entities.Ssd;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class SsdRequest {

    private String name;

    private String producer;

    private String formFactor;

    private String protocol;

    private String size;

    public Ssd toEntity() {
        Ssd ssd = new Ssd();
        ssd.setName(name);
        ssd.setProducer(producer);
        ssd.setFormFactor(formFactor);
        ssd.setProtocol(protocol);
        ssd.setSize(parseInteger(size));
        return ssd;
    }

}
