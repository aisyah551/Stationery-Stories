package dev.aisyah.stationery.repository;

import dev.aisyah.stationery.model.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId> {

    Optional<Order> findOrderByOrderId(int orderId);
}
