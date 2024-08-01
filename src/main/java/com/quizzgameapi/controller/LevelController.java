package com.quizzgameapi.controller;

import com.quizzgameapi.dto.LevelRequestDTO;
import com.quizzgameapi.dto.LevelResponseDTO;
import com.quizzgameapi.model.Level;
import com.quizzgameapi.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/levels")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @GetMapping
    public List<LevelResponseDTO> listAllLevels(){
        System.out.println("resgatando lista de níveis");

        return levelService.listAllLevels();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findLevelById(@PathVariable String id){
        try {
            System.out.println("resgatando nível por id: " + id);
            LevelResponseDTO levelResponseDTO = levelService.findByIdLevel(id);
            return ResponseEntity.ok().body(levelResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("desc/{desc}")
    public ResponseEntity<?> findLevelByDesc(@PathVariable String desc){
        try {
            System.out.println("resgatando nível por descrição: " + desc);
            LevelResponseDTO levelResponseDTO = levelService.findByDesc(desc);
            return ResponseEntity.ok().body(levelResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createLevel(@RequestBody LevelRequestDTO levelRequestDTO, UriComponentsBuilder uriBuilder){
        try {
            System.out.println("criando nível");
            Level level = levelService.createLevel(levelRequestDTO);
            URI uri = uriBuilder.path("/levels/"+ level.getIdLevel()).build().toUri();
            return ResponseEntity.created(uri).body(level);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateLevel(@PathVariable String id, @RequestBody LevelRequestDTO levelRequestDTO){
        System.out.println("alterando nível id: " + id);

        try {
            levelService.updateLevel(id, levelRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLevel(@PathVariable String id){
        System.out.println("deletando nível id: " + id);

        try {
            levelService.deleteLevelByIdLevel(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
