package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myshop.internetshop.classes.entities.Gpu;
import java.security.SecureRandom;
import java.util.Random;

public class GpuRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("producer")
    private String producer;

    @JsonProperty("boostClock")
    private String boostClock;

    @JsonProperty("vram")
    private String vram;

    @JsonProperty("tdp")
    private String tdp;

    @JsonProperty("hdmi")
    private int hdmi;

    @JsonProperty("displayPort")
    private int displayPort;

    @JsonProperty("dvi")
    private int dvi;

    @JsonProperty("vga")
    private int vga;

    @JsonProperty("price")
    private String price;

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

    public Integer parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }

        String numericValue = value.replaceAll("\\D", "");
        if (numericValue.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(numericValue);
    }

    public Float parseFloatNumber(String value) {
        if (value == null || value.isEmpty()) {
            Random rand = new SecureRandom();
            return rand.nextFloat(2000);
        }

        String numericValue = value.replaceAll("\\D", "");
        if (numericValue.isEmpty()) {
            Random rand = new SecureRandom();
            return rand.nextFloat(2000);
        }

        return Float.parseFloat(numericValue);
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
