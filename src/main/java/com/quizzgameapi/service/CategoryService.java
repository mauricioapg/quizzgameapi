package com.quizzgameapi.service;

import com.quizzgameapi.dto.CategoryRequestDTO;
import com.quizzgameapi.dto.CategoryResponseDTO;
import com.quizzgameapi.model.Category;
import com.quizzgameapi.repository.CategoryRepository;
import com.quizzgameapi.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<CategoryResponseDTO> listAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDTO> listCategoryResponseDTO = new ArrayList<>();

        categories.forEach((category -> {
            CategoryResponseDTO obj = new CategoryResponseDTO();
            obj.setIdCategory(category.getIdCategory());
            obj.setDesc(category.getDesc());

            listCategoryResponseDTO.add(obj);
        }));

        return listCategoryResponseDTO;
    }

    public CategoryResponseDTO findByIdCategory(String idCategory) throws Exception {
        Optional<Category> category = categoryRepository.findByIdCategory(idCategory);

        if(!category.isPresent()){
            throw new Exception("Nenhuma categoria encontrada com esse id: " + idCategory);
        }

        CategoryResponseDTO objResponse = new CategoryResponseDTO();
        objResponse.setIdCategory(category.get().getIdCategory());
        objResponse.setDesc(category.get().getDesc());

        return objResponse;
    }

    public Category createCategory(CategoryRequestDTO categoryRequestDTO) throws Exception{

        Category category = new Category();

        category.setIdCategory(UUID.randomUUID().toString());
        category.setDesc(categoryRequestDTO.getDesc());

        List<Category> categoriesFound = categoryRepository.findAllByDesc(category.getDesc());

        if(categoriesFound.isEmpty()){
            categoryRepository.save(category);
        }
        else{
            throw new Exception("Já existe uma categoria com essa descrição: " + categoryRequestDTO.getDesc());
        }

        return category;
    }

    public void updateCategory(String idCategory, CategoryRequestDTO categoryRequestDTO) throws Exception {

        Optional<Category> category = categoryRepository.findByIdCategory(idCategory);

        if(!category.isPresent()){
            throw new Exception("Nenhuma categoria encontrada com esse id: " + idCategory);
        }

        category.get().setDesc(categoryRequestDTO.getDesc());

        categoryRepository.save(category.get());
    }

    public void deleteCategoryByIdCategory(String idCategory) throws Exception {

        Optional<Category> category = categoryRepository.findByIdCategory(idCategory);

        if(!category.isPresent()){
            throw new Exception("Nenhuma categoria encontrada com esse id: " + idCategory);
        }

        categoryRepository.delete(category.get());
    }

}
