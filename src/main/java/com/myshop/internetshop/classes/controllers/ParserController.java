package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.dto.MotherboardRequest;
import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.services.ParserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/parser")
@Tag(name = "parser", description = "Controller to parse bad looking data to data, that" +
        " can be processed by other controllers")
public class ParserController {
    private final ParserService parserService;

    @Autowired
    ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @Operation(summary = "Parse multiple gpus to product json")
    @PostMapping("/gpu")
    public List<ProductDto> parseGpus(@RequestBody List<GpuRequest> gpus) {
        return parserService.massGpuParser(gpus);
    }

    @Operation(summary = "Parse multiple motherboards to product")
    @PostMapping("/motherboard")
    public List<ProductDto> parseMotherboards(@RequestBody List<MotherboardRequest> motherboards) {
        return parserService.massMotherboardParser(motherboards);
    }
}
