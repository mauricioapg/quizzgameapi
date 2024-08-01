package com.quizzgameapi.service;

import com.quizzgameapi.model.User;
import com.quizzgameapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
