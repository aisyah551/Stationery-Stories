package dev.aisyah.stationery.service;

import dev.aisyah.stationery.model.Stationery;
import dev.aisyah.stationery.repository.StationeryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationeryService {
    @Autowired
    private StationeryRepository stationeryRepository;

    public List<Stationery> allStationeries() {
        return stationeryRepository.findAll();
    }

    public Stationery getStationeryById(int productId) {
        Optional<Stationery> stationery = stationeryRepository.findStationeryByProductId(productId);
        return stationery.orElse(null);
    }

    public Stationery updateStationeryDetails(int productId, Stationery updatedStationery) {
        Optional<Stationery> existingStationery = stationeryRepository.findStationeryByProductId(productId);
        if (existingStationery.isPresent()) {
            Stationery stationeryToUpdate = existingStationery.get();
            stationeryToUpdate.setName(updatedStationery.getName());
            stationeryToUpdate.setPrice(updatedStationery.getPrice());
            stationeryToUpdate.setStock(updatedStationery.getStock());
            stationeryToUpdate.setImg(updatedStationery.getImg());
            stationeryToUpdate.setCategory(updatedStationery.getCategory());
            stationeryToUpdate.setSoldQuantity(updatedStationery.getSoldQuantity());
            stationeryToUpdate.setShortDescription(updatedStationery.getShortDescription());
            return stationeryRepository.save(stationeryToUpdate);
        }
        return null;
    }

    public Stationery createStationery(Stationery newStationery) {
        // Set initial values for new product
        newStationery.setSoldQuantity(0);

        return stationeryRepository.save(newStationery);
    }

    public Stationery updateStockAndSoldQuantity(int productId, int quantitySold) {
        Optional<Stationery> stationeryOpt = stationeryRepository.findStationeryByProductId(productId);

        if (stationeryOpt.isPresent()) {
            Stationery stationery = stationeryOpt.get();
            if (stationery.getStock() >= quantitySold) {
                stationery.setStock(stationery.getStock() - quantitySold);
                stationery.setSoldQuantity(stationery.getSoldQuantity() + quantitySold);
                return stationeryRepository.save(stationery);
            } else {
                throw new IllegalStateException("Insufficient stock for product: " + productId);
            }
        }
        throw new IllegalArgumentException("Product not found: " + productId);
    }

    public List<Stationery> getStationeriesByCategory(String category) {
        return stationeryRepository.findAll().stream()
                .filter(stationery -> stationery.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Stationery> getStationeriesByKeyword(String keyword) {
        String lowercaseKeyword = keyword.toLowerCase();
        return stationeryRepository.findAll().stream()
                .filter(stationery ->
                        stationery.getName().toLowerCase().contains(lowercaseKeyword) ||
                                stationery.getShortDescription().toLowerCase().contains(lowercaseKeyword))
                .collect(Collectors.toList());
    }

    public List<Stationery> getAllStationeriesSortedBySoldQuantity(boolean ascending) {
        List<Stationery> allStationeries = stationeryRepository.findAll();
        Comparator<Stationery> comparator = Comparator.comparingInt(Stationery::getSoldQuantity);

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return allStationeries.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Stationery getStationeryWithHighestSoldQuantity() {
        return stationeryRepository.findAll().stream()
                .max(Comparator.comparingInt(Stationery::getSoldQuantity))
                .orElse(null);
    }
}
