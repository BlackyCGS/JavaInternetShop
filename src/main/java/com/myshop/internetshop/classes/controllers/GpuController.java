package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.GpuDto;
import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.services.GpuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/products/gpu")
public class GpuController {

    private final GpuService gpuService;

    @Autowired
    public GpuController(GpuService gpuService) {
        this.gpuService = gpuService;
    }

    @GetMapping("/all")
    public List<GpuDto> getAllGpus(@RequestParam(required = false) String producer,
                            @RequestParam(required = false, defaultValue = "0") Integer boostClock,
                            @RequestParam(required = false, defaultValue = "0") Integer displayPort,
                            @RequestParam(required = false, defaultValue = "0") Integer hdmi,
                            @RequestParam(required = false, defaultValue = "0") Integer tdp,
                            @RequestParam(required = false, defaultValue = "0") Integer vram) {
        return gpuService.getAllGpus(producer, boostClock, displayPort, hdmi, tdp, vram);
    }

    @GetMapping("/{id}")
    public GpuDto getGpuById(@PathVariable int id) {
        return gpuService.getGpuById(id);
    }

    @GetMapping("/name/{name}")
    public List<GpuDto> getGpuByName(@PathVariable String name) {
        return gpuService.getGpuByName(name);
    }

    @PostMapping
    public Gpu createGpu(@RequestBody GpuRequest gpu) {
        Gpu gpuTmp = gpu.toEntity();
        return gpuService.saveGpu(gpuTmp);
    }

    @PostMapping("/list")
    public ResponseEntity<String> createGpus(@RequestBody List<GpuRequest> gpus) {
        for (GpuRequest gpu : gpus) {
            Gpu gpuTmp = gpu.toEntity();
            gpuService.saveGpu(gpuTmp);
        }
        return ResponseEntity.ok("Gpus created");
    }

    @DeleteMapping("/{id}")
    public void deleteGpu(@PathVariable int id) {
        gpuService.deleteGpu(id);
    }
}
