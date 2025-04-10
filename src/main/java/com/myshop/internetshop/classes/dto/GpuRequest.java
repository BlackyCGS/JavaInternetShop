package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myshop.internetshop.classes.entities.Gpu;
import lombok.Getter;

import static com.myshop.internetshop.classes.utilities.NumberParser.parseFloatNumber;
import static com.myshop.internetshop.classes.utilities.NumberParser.parseInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class GpuRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("producer")
    String producer;

    @JsonProperty("boostClock")
    String boostClock;

    @JsonProperty("vram")
    String vram;

    @JsonProperty("tdp")
    String tdp;

    @JsonProperty("hdmi")
    int hdmi;

    @JsonProperty("displayPort")
    int displayPort;

    @JsonProperty("dvi")
    int dvi;

    @JsonProperty("vga")
    int vga;

    @JsonProperty("price")
    String price;

    public void setBaseInfo(String name, String producer, String boostClock,
                            String vram, String price) {
        this.name = name;
        this.producer = producer;
        this.boostClock = boostClock;
        this.vram = vram;
        this.price = price;
    }

    public void setAdditionalInfo(int displayPort, int dvi, int hdmi, String tdp,
                                  int vga) {
        this.displayPort = displayPort;
        this.dvi = dvi;
        this.hdmi = hdmi;
        this.tdp = tdp;
        this.vga = vga;

    }



    public Gpu toEntity() {
        Gpu gpu = new Gpu();
        gpu.setName(this.name);
        gpu.setProducer(this.producer);
        gpu.setBoostClock(parseInteger(this.boostClock));
        gpu.setVram(parseInteger(this.vram));
        gpu.setTdp(parseInteger(this.tdp));
        gpu.setHdmi(this.hdmi);
        gpu.setDisplayPort(this.displayPort);
        gpu.setDvi(this.dvi);
        gpu.setVga(this.vga);
        gpu.setPrice(parseFloatNumber(this.price));
        return gpu;
    }

}
