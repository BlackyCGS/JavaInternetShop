package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.GpuDto;
import com.myshop.internetshop.classes.services.GpuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO Consider delete this class so all products will be processed through
//  ProductsController
@RestController
@RequestMapping("/api/products/gpu")
public class GpuController {

    private final GpuService gpuService;

    @Autowired
    public GpuController(GpuService gpuService) {
        this.gpuService = gpuService;
    }

    @GetMapping("/{id}")
    public GpuDto getGpuById(@PathVariable("id") int id) {
        return gpuService.getGpuById(id);
    }

    @GetMapping("/name/{name}")
    public List<GpuDto> getGpuByName(@PathVariable("name") String name) {
        return gpuService.getGpuByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteGpu(@PathVariable("id") int id) {
        gpuService.deleteGpu(id);
    }
}
