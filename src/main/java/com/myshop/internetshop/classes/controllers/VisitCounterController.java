package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.services.VisitCounterService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/visits")
public class VisitCounterController {

    VisitCounterService visitCounterService;

    @Autowired
    public VisitCounterController(VisitCounterService visitCounterService) {
        this.visitCounterService = visitCounterService;
    }

    @Operation(summary = "Get total count of calls")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Map<String, Integer>> getTotalVisits() {
        Map<String, Integer> totalVisits = visitCounterService.getTotalVisits();
        if (totalVisits.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(visitCounterService.getTotalVisits());
    }
}
