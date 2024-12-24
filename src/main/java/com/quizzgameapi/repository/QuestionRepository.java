package com.quizzgameapi.repository;

import com.quizzgameapi.model.Question;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends MongoRepository<Question, ObjectId> {

    Optional<Question> findByIdQuestion(String idQuestion);

    List<Question> findAllByCategory(String idCategory);

    List<Question> findAllByLevel(String idLevel);

}
