package dev.aisyah.stationery.controller;

import dev.aisyah.stationery.service.SaleStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/sales-report")
public class SaleStatsController {
    @Autowired
    private SaleStatsService saleStatsService;

    // done
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSalesStats(@RequestParam(defaultValue = "false") boolean ascending) {
        Map<String, Object> salesStats = saleStatsService.getSalesStats(ascending);
        return new ResponseEntity<>(salesStats, HttpStatus.OK);
    }
}
