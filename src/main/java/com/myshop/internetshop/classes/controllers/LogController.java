package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.services.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Logs", description = "Controller for getting logs")
public class LogController {

    LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @Operation(summary = "Get logs from certain date")
    @GetMapping("/{date}")
    public ResponseEntity<InputStreamResource> getLogsByDate(@Parameter(description =
            "date in yyyy-MM-dd format") @PathVariable String date) {
        return logService.getLogsByDate(date);
    }
}
