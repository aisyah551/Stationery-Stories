package dev.aisyah.stationery.service;

import dev.aisyah.stationery.model.*;
import dev.aisyah.stationery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private StationeryService stationeryService;

    private double calculateDeliveryFee(String location) {
        return "WM".equalsIgnoreCase(location) ? 5.00 :
                "EM".equalsIgnoreCase(location) ? 9.00 : 0.00;
    }

    private double getTotalWithDelivery(double deliveryFee) {
        double total = cartService.getCartTotal() + deliveryFee;
        BigDecimal roundedTotal = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
        return roundedTotal.doubleValue();
    }

    public Order createOrder(String location) {
        ShoppingCart cart = cartService.getCart();
        double deliveryFee = calculateDeliveryFee(location);
        double total = getTotalWithDelivery(deliveryFee);

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // Update stock and soldQuantity for each item in the cart
        for (OrderItem item : cart.getItems()) {
            try {
                stationeryService.updateStockAndSoldQuantity(item.getProduct().getProductId(), item.getQuantity());
            } catch (IllegalStateException | IllegalArgumentException e) {
                // If there's an error updating any product, we should not proceed with the order
                throw new IllegalStateException("Error processing order: " + e.getMessage());
            }
        }

        Order order = new Order();
        order.setDate(new Date());
        order.setOrderStatus("Order in Progress");
        order.setShoppingCart(cart);
        order.setLocation(location);
        order.setDeliveryFee(deliveryFee);
        order.setTotalWithDeliveryFee(total);
        order.setOrderId();

        // debugging logs
        System.out.println("Cart Total: " + cartService.getCartTotal());
        System.out.println("Delivery Fee: " + deliveryFee);
        System.out.println("Total With Delivery: " + total);
        System.out.println("Order ID: " + order.getOrderId());

        Order savedOrder = orderRepository.save(order);

        // Clear the cart after successful order creation
        cartService.clearCart();

        return savedOrder;
    }

    public Order updateOrderStatus(int orderId, String newStatus) {
        Optional<Order> existingOrder = orderRepository.findOrderByOrderId(orderId);
        if (existingOrder.isPresent()) {
            Order orderToUpdate = existingOrder.get();
            orderToUpdate.setOrderStatus(newStatus);
            return orderRepository.save(orderToUpdate);
        }
        return null;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int orderId) {
        Optional<Order> order = orderRepository.findOrderByOrderId(orderId);
        return order.orElse(null);
    }

    public Order addReviewToOrder(int orderId, int rating, String comment) {
        Order order = getOrderById(orderId);
        if (order != null) {
            if (order.getReview() != null) {
                throw new IllegalStateException("Review already exists for this order");
            }
            Review review = new Review(rating, comment);
            order.setReview(review);
            return orderRepository.save(order);
        }
        throw new IllegalArgumentException("Order not found");
    }
}
