package dev.aisyah.stationery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private ObjectId id;
    private String orderStatus;
    private String location;
    private double deliveryFee;
    private double totalWithDeliveryFee;
    private Date date;
    private ShoppingCart shoppingCart;
    private static AtomicInteger orderIdCounter = new AtomicInteger(1);
    private int orderId;
    private Review review;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId() {
        this.orderId = orderIdCounter.getAndIncrement();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getTotalWithDeliveryFee() {
        return totalWithDeliveryFee;
    }

    public void setTotalWithDeliveryFee(double totalWithDeliveryFee) {
        this.totalWithDeliveryFee = totalWithDeliveryFee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
