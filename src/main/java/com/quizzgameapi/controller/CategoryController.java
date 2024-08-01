package com.quizzgameapi.controller;

import com.quizzgameapi.dto.CategoryRequestDTO;
import com.quizzgameapi.dto.CategoryResponseDTO;
import com.quizzgameapi.model.Category;
import com.quizzgameapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDTO> listAllCategories(){
        System.out.println("resgatando lista de categorias");

        return categoryService.listAllCategories();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable String id){
        System.out.println("resgatando categoria por id: " + id);

        try {
            CategoryResponseDTO categoryResponseDTO = categoryService.findByIdCategory(id);
            return ResponseEntity.ok().body(categoryResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO, UriComponentsBuilder uriBuilder){
        System.out.println("criando nova categoria");

        try {
            Category category = categoryService.createCategory(categoryRequestDTO);
            URI uri = uriBuilder.path("/categories/"+ category.getIdCategory()).build().toUri();
            return ResponseEntity.created(uri).body(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody CategoryRequestDTO categoryRequestDTO){
        System.out.println("alterando categoria id: " + id);

        try {
            categoryService.updateCategory(id, categoryRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        System.out.println("deletando categoria id: " + id);

        try {
            categoryService.deleteCategoryByIdCategory(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
