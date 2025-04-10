package com.myshop.internetshop.classes.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myshop.internetshop.classes.entities.Motherboard;
import static com.myshop.internetshop.classes.utilities.NumberParser.parseFloatNumber;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MotherboardRequest {

    @JsonProperty("Name")
    String name;

    @JsonProperty("Price")
    String price;

    @JsonProperty("Producer")
    String producer;

    @JsonProperty("Socket")
    String socket;

    @JsonProperty("Chipset")
    String chipset;

    @JsonProperty("Form Factor")
    String formFactor;

    @JsonProperty("Memory Type")
    String memoryType;

    @JsonProperty("SATA")
    int sata;

    @JsonProperty("RAM Slots")
    int ramSlots;

    @JsonProperty("VGA")
    int vga;

    @JsonProperty("DVI")
    int dvi;

    @JsonProperty("Display Port")
    int displayPort;

    @JsonProperty("HDMI")
    int hdmi;

    public Motherboard toEntity() {
        Motherboard motherboard = new Motherboard();
        motherboard.setName(this.name);
        motherboard.setPrice(parseFloatNumber(this.price));
        motherboard.setProducer(this.producer);
        motherboard.setSocket(this.socket);
        motherboard.setChipset(this.chipset);
        motherboard.setFormFactor(this.formFactor);
        motherboard.setMemoryType(this.memoryType);
        motherboard.setSata(this.sata);
        motherboard.setRamSlots(this.ramSlots);
        motherboard.setVga(this.vga);
        motherboard.setDvi(this.dvi);
        motherboard.setDisplayPort(this.displayPort);
        motherboard.setHdmi(this.hdmi);
        return motherboard;
    }
}
