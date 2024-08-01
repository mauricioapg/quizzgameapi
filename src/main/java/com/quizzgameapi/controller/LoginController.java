package com.quizzgameapi.controller;

import com.quizzgameapi.dto.LoginRequestDTO;
import com.quizzgameapi.dto.LoginResponseDTO;
import com.quizzgameapi.model.User;
import com.quizzgameapi.service.UserService;
import com.quizzgameapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<User> user = userService.loadUserByUsername(loginRequestDTO.getUsername());

        if(!user.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else if(loginRequestDTO.getUsername().equals(user.get().getUsername()) &&
                loginRequestDTO.getPassword().equals(user.get().getPassword())){
            final String token = jwtUtil.generateToken(loginRequestDTO.getUsername());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

