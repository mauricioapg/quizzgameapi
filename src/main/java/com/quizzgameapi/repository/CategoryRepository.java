package com.quizzgameapi.repository;

import com.quizzgameapi.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId> {

    Optional<Category> findByIdCategory(String idCategory);
    Category findOneByIdCategory(String idCategory);
    List<Category> findAllByDesc(String desc);

}
