package dev.aisyah.stationery.controller;

import dev.aisyah.stationery.model.OrderItem;
import dev.aisyah.stationery.service.ShoppingCartService;
import dev.aisyah.stationery.model.Stationery;
import dev.aisyah.stationery.service.StationeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private StationeryService stationeryService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> request) {
        int productId = (Integer) request.get("productId");
        int quantity = (Integer) request.get("quantity");

        Stationery product = stationeryService.getStationeryById(productId);
        if (product == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        cartService.addToCart(product, quantity);
        return ResponseEntity.ok("Product added to cart");
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable int productId) {
        cartService.removeFromCart(productId);
        return ResponseEntity.ok("Product removed from cart");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(@RequestBody Map<String, Object> request) {
        int productId = (Integer) request.get("productId");
        int quantity = (Integer) request.get("quantity");

        cartService.updateQuantity(productId, quantity);
        return ResponseEntity.ok("Quantity updated");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Cart cleared");
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getCartContents() {
        return ResponseEntity.ok(cartService.getCartContents());
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getCartTotal() {
        return ResponseEntity.ok(cartService.getCartTotal());
    }
}
