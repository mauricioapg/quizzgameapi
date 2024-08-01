package com.quizzgameapi.repository;

import com.quizzgameapi.model.Level;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LevelRepository extends MongoRepository<Level, ObjectId> {

    Optional<Level> findByIdLevel(String idLevel);
    Optional<Level> findByDesc(String desc);

    List<Level> findAllByDesc(String desc);

}
