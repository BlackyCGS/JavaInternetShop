package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.*;
import com.myshop.internetshop.classes.services.ParserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/parser")
@Tag(name = "parser", description = "Controller to parse bad looking data to data, that"
        + " can be processed by other controllers")
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

    @Operation(summary = "Parse multiple cases to product")
    @PostMapping("/case")
    public List<ProductDto> parseCases(@RequestBody List<CaseRequest> cases) {
        return parserService.massCaseParser(cases);
    }

    @Operation(summary = "Parse multiple rams to product")
    @PostMapping("/ram")
    public List<ProductDto> parseRam(@RequestBody List<RamRequest> rams) {
        return parserService.massRamParser(rams);
    }

    @Operation(summary = "Parse multiple cpus to product")
    @PostMapping("/cpu")
    public List<ProductDto> parseCpu(@RequestBody List<CpuRequest> cpus) {
        return parserService.massCpuParser(cpus);
    }

    @Operation(summary = "Parse multiple psus to product")
    @PostMapping("/psu")
    public List<ProductDto> parsePsu(@RequestBody List<PsuRequest> psus) {
        return parserService.massPsuParser(psus);
    }

    @Operation(summary = "Parse multiple hdds to product")
    @PostMapping("/hdd")
    public List<ProductDto> parseHdd(@RequestBody List<HddRequest> hdds) {
        return parserService.massHddParser(hdds);
    }

    @Operation(summary = "Parse multiple ssds to product")
    @PostMapping("/ssd")
    public List<ProductDto> parseSsd(@RequestBody List<SsdRequest> ssds) {
        return parserService.massSsdParser(ssds);
    }

}
