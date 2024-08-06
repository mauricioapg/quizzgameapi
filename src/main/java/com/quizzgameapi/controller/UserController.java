package com.quizzgameapi.controller;

import com.quizzgameapi.dto.CategoryRequestDTO;
import com.quizzgameapi.model.User;
import com.quizzgameapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("username/{username}")
    public ResponseEntity<?> findUserById(@PathVariable String username){
        System.out.println("resgatando usuário por username: " + username);

        try {
            Optional<User> categoryResponseDTO = userService.findUserByUsername(username);
            return ResponseEntity.ok().body(categoryResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("{id}/question/{idQuestion}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @PathVariable String idQuestion){
        System.out.println("adicionando pergunta respondida ao usuário id: " + id);

        try {
            userService.addQuestionAnswered(id, idQuestion);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
