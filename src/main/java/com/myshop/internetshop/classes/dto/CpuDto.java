package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Cpu;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CpuDto {


    private Integer productId;

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "producer can not be blank")
    private String producer;

    @NotBlank(message = "cores can not be blank")
    private int cores;

    @NotBlank(message = "threads can not be blank")
    private int threads;

    @NotBlank(message = "tdp can not be blank")
    private int tdp;

    @NotBlank(message = "socket can not be blank")
    private String socket;

    public CpuDto(Cpu cpu) {
        this.productId = cpu.getProductId();
        this.name = cpu.getName();
        this.producer = cpu.getProducer();
        this.cores = cpu.getCores();
        this.threads = cpu.getThreads();
        this.tdp = cpu.getTdp();
        this.socket = cpu.getSocket();
    }

    public Cpu toEntity() {
        Cpu cpu = new Cpu();
        cpu.setProductId(productId);
        cpu.setName(name);
        cpu.setProducer(producer);
        cpu.setCores(cores);
        cpu.setThreads(threads);
        cpu.setTdp(tdp);
        cpu.setSocket(socket);
        return cpu;
    }
}
