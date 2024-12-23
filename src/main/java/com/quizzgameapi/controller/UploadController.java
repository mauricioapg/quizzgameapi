package com.quizzgameapi.controller;

import com.quizzgameapi.service.GoogleCloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = googleCloudStorageService.uploadFile(file);
            return ResponseEntity.ok("Arquivo carregado com sucesso: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Falha ao carregar arquivo: " + e.getMessage());
        }
    }
}
