package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myshop.internetshop.classes.entities.Gpu;

public class GpuRequest {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Producer")
    private String producer;

    @JsonProperty("Boost Clock")
    private String boostClock;

    @JsonProperty("Vram")
    private String vram;

    @JsonProperty("TDP")
    private String tdp;

    @JsonProperty("HDMI")
    private String hdmi;

    @JsonProperty("DisplayPort")
    private String displayPort;

    @JsonProperty("DVI")
    private String dvi;

    @JsonProperty("VGA")
    private String vga;

    public Integer parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        String numericValue = value.replaceAll("\\D", "");
        if (numericValue.isEmpty()) {
            return null;
        }

        return Integer.parseInt(numericValue);
    }

    public Gpu toEntity() {
        Gpu gpu = new Gpu();
        gpu.setName(this.name);
        gpu.setProducer(this.producer);
        gpu.setBoostClock(parseInteger(this.boostClock));
        gpu.setVram(parseInteger(this.vram));
        gpu.setTdp(parseInteger(this.tdp));
        gpu.setHdmi(parseInteger(this.hdmi));
        gpu.setDisplayPort(parseInteger(this.displayPort));
        gpu.setDvi(parseInteger(this.dvi));
        gpu.setVga(parseInteger(this.vga));
        return gpu;
    }
}
