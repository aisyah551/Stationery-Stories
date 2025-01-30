package dev.aisyah.stationery.repository;

import dev.aisyah.stationery.model.Stationery;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationeryRepository extends MongoRepository<Stationery, ObjectId> {

    Optional<Stationery> findStationeryByProductId(int productId);
}
