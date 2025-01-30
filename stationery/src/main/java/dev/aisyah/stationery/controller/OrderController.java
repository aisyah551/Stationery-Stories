package dev.aisyah.stationery.controller;

import dev.aisyah.stationery.service.OrderService;
import dev.aisyah.stationery.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // done
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Map<String, String> request) {
        String location = request.get("location");

        if (location == null || (!location.equalsIgnoreCase("WM") && !location.equalsIgnoreCase("EM"))) {
            return ResponseEntity.badRequest().body("Invalid location. Must be 'WM' or 'EM'");
        }

        try {
            Order order = orderService.createOrder(location);
            return ResponseEntity.ok(Map.of(
                    "message", "Order created successfully",
                    "orderId", order.getId(),
                    "location", order.getLocation(),
                    "total", order.getTotalWithDeliveryFee(),
                    "deliveryFee", order.getDeliveryFee()
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // done
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable int orderId, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");

        if (newStatus == null || newStatus.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        if (updatedOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // done
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable int orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // done
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // done
    @PostMapping("/{orderId}/review")
    public ResponseEntity<?> addReviewToOrder(@PathVariable int orderId, @RequestBody Map<String, Object> reviewData) {
        try {
            int rating = (int) reviewData.get("rating");
            String comment = (String) reviewData.get("comment");

            Order updatedOrder = orderService.addReviewToOrder(orderId, rating, comment);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
