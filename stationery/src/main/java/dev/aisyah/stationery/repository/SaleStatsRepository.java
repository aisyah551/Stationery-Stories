package dev.aisyah.stationery.repository;

import dev.aisyah.stationery.model.SaleStats;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleStatsRepository extends MongoRepository<SaleStats, ObjectId> {
}
