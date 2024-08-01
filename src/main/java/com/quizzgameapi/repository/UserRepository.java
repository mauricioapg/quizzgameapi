package com.quizzgameapi.repository;

import com.quizzgameapi.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findByIdUser(String idUser);
    List<User> findAllByType(String type);

}
