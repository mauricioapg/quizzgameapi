package com.quizzgameapi.controller;

import com.quizzgameapi.dto.QuestionRequestDTO;
import com.quizzgameapi.dto.QuestionResponseDTO;
import com.quizzgameapi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<QuestionResponseDTO> listAllQuestions(){
        System.out.println("resgatando lista de perguntas");

        return questionService.listAllQuestions();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findQuestionById(@PathVariable String id){
        System.out.println("resgatando pergunta por id: " + id);

        try {
            QuestionResponseDTO questionResponseDTO = questionService.findByIdQuestion(id);
            return ResponseEntity.ok().body(questionResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("id-question/{id}")
    public ResponseEntity<?> findAlternativesByIdQuestion(@PathVariable String id){
        System.out.println("resgatando alternativas por idQuestion: " + id);

        try {
            List<String> alternatives = questionService.findAlternativesByIdQuestion(id);
            return ResponseEntity.ok().body(alternatives);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("category/{idCategory}")
    public List<QuestionResponseDTO> findQuestionsByIdCategory(@PathVariable String idCategory){
        System.out.println("resgatando lista de perguntas por id de categoria: " + idCategory);

        return questionService.findAllByIdCategory(idCategory);
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO, UriComponentsBuilder uriBuilder){
        System.out.println("criando nova pergunta");

        try {
            QuestionResponseDTO questionResponseDTO = questionService.createQuestion(questionRequestDTO);
            URI uri = uriBuilder.path("/questions/"+ questionResponseDTO.getIdQuestion()).build().toUri();
            return ResponseEntity.created(uri).body(questionResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable String id, @RequestBody QuestionRequestDTO questionRequestDTO){
        System.out.println("alterando pergunta id: " + id);

        try {
            questionService.updateQuestion(id, questionRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        System.out.println("deletando pergunta id: " + id);

        try {
            questionService.deleteQuestionByIdQuestion(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
