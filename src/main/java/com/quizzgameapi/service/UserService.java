package com.quizzgameapi.service;

import com.quizzgameapi.dto.CategoryRequestDTO;
import com.quizzgameapi.exception.CategoryException;
import com.quizzgameapi.model.Category;
import com.quizzgameapi.model.User;
import com.quizzgameapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);

        if(!user.isPresent()){
            throw new CategoryException("Nenhum usuário encontrado com esse username: " + username);
        }

        return user;
    }

    public void addQuestionAnswered(String idUser, String idQuestion) throws CategoryException {

        Optional<User> user = userRepository.findByIdUser(idUser);

        if(!user.isPresent()){
            throw new CategoryException("Nenhum usuário encontrado com esse id: " + idUser);
        }

        List<String> questionsAnswered = user.get().getQuestionsAnswered();
        questionsAnswered.add(idQuestion);

        user.get().setQuestionsAnswered(questionsAnswered);

        userRepository.save(user.get());
    }

}
