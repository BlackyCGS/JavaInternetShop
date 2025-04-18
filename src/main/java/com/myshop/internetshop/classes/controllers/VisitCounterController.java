package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.services.VisitCounterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/visits")
public class VisitCounterController {

    VisitCounterService visitCounterService;

    @Autowired
    public VisitCounterController(VisitCounterService visitCounterService) {
        this.visitCounterService = visitCounterService;
    }

    @Operation(summary = "Get count of calls to certain url")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{url}")
    public ResponseEntity<Integer> getVisitsByUrl(@PathVariable String url) {
        return ResponseEntity.ok(visitCounterService.getUrlVisitsByUrl(url));
    }

    @Operation(summary = "Get total count of calls")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Map<String, Integer>> getTotalVisits() {
        return ResponseEntity.ok(visitCounterService.getTotalVisits());
    }
}
