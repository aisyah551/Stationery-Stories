package dev.aisyah.stationery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Stationery product;
    private int quantity;

    public OrderItem(Stationery product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Stationery getProduct() {
        return product;
    }

    public void setProduct(Stationery product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
