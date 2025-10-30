package com.myshop.internetshop.classes.dto;

import static com.myshop.internetshop.classes.utilities.NumberParser.parseInteger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myshop.internetshop.classes.entities.Cpu;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CpuRequest {

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "producer can not be blank")
    private String producer;

    @NotBlank(message = "cores can not be blank")
    private int cores;

    @NotBlank(message = "threads can not be blank")
    private int threads;

    @NotBlank(message = "tdp can not be blank")
    private String tdp;

    @NotBlank(message = "socket can not be blank")
    private String socket;

    public Cpu toEntity() {
        Cpu cpu = new Cpu();
        cpu.setName(name);
        cpu.setProducer(producer);
        cpu.setCores(cores);
        cpu.setThreads(threads);
        cpu.setTdp(parseInteger(tdp));
        cpu.setSocket(socket);
        return cpu;
    }
}
