package dev.aisyah.stationery.service;

import dev.aisyah.stationery.model.Stationery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleStatsService {
    @Autowired
    private StationeryService stationeryService;

    public Map<String, Object> getSalesStats(boolean ascending) {
        List<Stationery> sortedStationeries = stationeryService.getAllStationeriesSortedBySoldQuantity(ascending);
        Stationery highestSelling = stationeryService.getStationeryWithHighestSoldQuantity();

        Map<String, Object> salesStats = new HashMap<>();
        salesStats.put("sortedStationeries", sortedStationeries);
        salesStats.put("highestSelling", highestSelling);

        return salesStats;
    }
}
