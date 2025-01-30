package dev.aisyah.stationery.controller;

import dev.aisyah.stationery.service.StationeryService;
import dev.aisyah.stationery.model.Stationery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stationeries")
public class StationeryController {
    @Autowired
    private StationeryService stationeryService;

    @GetMapping
    public ResponseEntity<List<Stationery>> getAllStationeries() {
        return new ResponseEntity<>(stationeryService.allStationeries(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Stationery> getStationeryById(@PathVariable int productId) {
        Stationery stationery = stationeryService.getStationeryById(productId);
        if (stationery == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stationery, HttpStatus.OK);
    }

    // done
    @PutMapping("/update/{productId}")
    public ResponseEntity<Stationery> updateStationeryDetails(@PathVariable int productId, @RequestBody Stationery updatedStationery) {
        Stationery stationery = stationeryService.updateStationeryDetails(productId, updatedStationery);

        if (stationery == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stationery, HttpStatus.OK);
    }

    // done
    @PostMapping("/addProduct")
    public ResponseEntity<Stationery> createStationery(@RequestBody Stationery newStationery) {
        Stationery createdStationery = stationeryService.createStationery(newStationery);

        return new ResponseEntity<>(createdStationery, HttpStatus.CREATED);
    }

    // done
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Stationery>> getStationeriesByCategory(@PathVariable String category) {
        List<Stationery> stationeries = stationeryService.getStationeriesByCategory(category);
        if (stationeries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stationeries, HttpStatus.OK);
    }

    // done
    @PostMapping("/search")
    public ResponseEntity<List<Stationery>> getStationeriesByKeyword(@RequestBody Map<String, String> body) {
        String keyword = body.get("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Stationery> stationeries = stationeryService.getStationeriesByKeyword(keyword);
        if (stationeries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stationeries, HttpStatus.OK);
    }

    //todo: add product to wishlist service
}
