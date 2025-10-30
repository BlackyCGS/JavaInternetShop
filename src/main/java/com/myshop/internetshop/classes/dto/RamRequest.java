package com.myshop.internetshop.classes.dto;

import static com.myshop.internetshop.classes.utilities.NumberParser.parseInteger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myshop.internetshop.classes.entities.Ram;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class RamRequest {

    private String name;

    private String producer;

    private String ramType;

    @JsonProperty("Size")
    private String size;

    private String timings;

    public Ram toEntity() {
        Ram ram = new Ram();
        ram.setName(name);
        ram.setProducer(producer);
        ram.setRamType(ramType);
        ram.setSize(parseInteger(size));
        ram.setTimings(timings);
        return ram;
    }

}
