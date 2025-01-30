package dev.aisyah.stationery.service;

import dev.aisyah.stationery.model.OrderItem;
import dev.aisyah.stationery.model.Stationery;
import dev.aisyah.stationery.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    private ShoppingCart cart = new ShoppingCart();

    public void addToCart(Stationery product, int quantity) {
        List<OrderItem> items = cart.getItems();
        Optional<OrderItem> existingItem = items.stream()
                .filter(item -> item.getProduct().getProductId() == product.getProductId())
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            items.add(new OrderItem(product, quantity));
        }
    }

    public void removeFromCart(int productId) {
        cart.getItems().removeIf(item -> item.getProduct().getProductId() == productId);
    }

    public void updateQuantity(int productId, int quantity) {
        cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId() == productId)
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    public void clearCart() {
        cart.getItems().clear();
    }

    public List<OrderItem> getCartContents() {
        return cart.getItems();
    }

    public double getCartTotal() {
        return cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public ShoppingCart getCart() {
        return cart;
    }
}
