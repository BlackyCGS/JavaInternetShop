package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.LogStatusDto;
import com.myshop.internetshop.classes.services.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Logs", description = "Controller for getting logs")
public class LogController {

    final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @Operation(summary = "Get logs from certain date")
    @GetMapping("/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> getLogsByDate(@Parameter(description =
            "date in yyyy-MM-dd format") @PathVariable String date) {
        return logService.getLogsByDate("", date);
    }

    @Operation(summary = "Get warn logs from certain date")
    @GetMapping("/{date}/warn")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> getWarnLogsByDate(@Parameter(description =
            "date in yyyy-MM-dd format") @PathVariable String date) {
        return logService.getLogsByDate("Warn", date);
    }

    @Operation(summary = "Start log generation from certain period of time")
    @GetMapping("/period")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> startLogGeneration(
            @RequestParam(defaultValue = "") String additional,
            @RequestParam String start,
            @RequestParam String end) {
        return ResponseEntity.ok(logService.startGeneration(additional, start, end));
    }

    @Operation(summary = "Get log generation status by id")
    @GetMapping("/period/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LogStatusDto> getLogGenerationStatus(@RequestParam String id) {
        return ResponseEntity.ok(logService.getTaskById(id));
    }

    @Operation(summary = "Get generated log file by id")
    @GetMapping("/period/get")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> getLogFile(@RequestParam String id) {
        ResponseEntity<InputStreamResource> response =
                logService.getGeneratedLogsById(id);
        logService.deleteLogFileByUid(id);

        return response;
    }
}
