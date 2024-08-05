package com.quizzgameapi.service;

import com.quizzgameapi.dto.QuestionRequestDTO;
import com.quizzgameapi.dto.QuestionResponseDTO;
import com.quizzgameapi.exception.QuestionException;
import com.quizzgameapi.model.Category;
import com.quizzgameapi.model.Question;
import com.quizzgameapi.repository.CategoryRepository;
import com.quizzgameapi.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<QuestionResponseDTO> listAllQuestions(){
        List<Question> questions = questionRepository.findAll();
        List<QuestionResponseDTO> listCategoryResponseDTO = new ArrayList<>();

        questions.forEach((question -> {

            Category categoryFound = categoryRepository.findOneByIdCategory(question.getCategory());

            QuestionResponseDTO obj = new QuestionResponseDTO();
            obj.setIdQuestion(question.getIdQuestion());
            obj.setTitle(question.getTitle());
            obj.setCategory(categoryFound.getDesc());
            obj.setLevel(question.getLevel());
            obj.setAlternatives(question.getAlternatives());
            obj.setAnswer(question.getAnswer());

            listCategoryResponseDTO.add(obj);
        }));

        return listCategoryResponseDTO;
    }

    public QuestionResponseDTO findByIdQuestion(String idQuestion) throws QuestionException {
        Optional<Question> question = questionRepository.findByIdQuestion(idQuestion);

        if(!question.isPresent()){
            throw new QuestionException("Nenhuma pergunta encontrada com esse id: " + idQuestion);
        }

        Category categoryFound = categoryRepository.findOneByIdCategory(question.get().getCategory());

        QuestionResponseDTO objResponse = new QuestionResponseDTO();
        objResponse.setIdQuestion(question.get().getIdQuestion());
        objResponse.setTitle(question.get().getTitle());
        objResponse.setCategory(categoryFound.getDesc());
        objResponse.setLevel(question.get().getLevel());
        objResponse.setAlternatives(question.get().getAlternatives());
        objResponse.setAnswer(question.get().getAnswer());

        return objResponse;
    }

    public List<QuestionResponseDTO> findAllByIdCategory(String idCategory) {
        List<Question> questions = questionRepository.findAllByCategory(idCategory);
        List<QuestionResponseDTO> responseList = new ArrayList<>();

        questions.forEach((question -> {
            Category categoryFound = categoryRepository.findOneByIdCategory(question.getCategory());

            QuestionResponseDTO objResponse = new QuestionResponseDTO();
            objResponse.setIdQuestion(question.getIdQuestion());
            objResponse.setTitle(question.getTitle());
            objResponse.setCategory(categoryFound.getDesc());
            objResponse.setLevel(question.getLevel());
            objResponse.setAlternatives(question.getAlternatives());
            objResponse.setAnswer(question.getAnswer());

            responseList.add(objResponse);
        }));

        return responseList;
    }

    public QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO){

        Question question = new Question();
        QuestionResponseDTO objResponse = new QuestionResponseDTO();

        List<Category> categoryList = categoryRepository.findAllByDesc(questionRequestDTO.getCategory());

        question.setIdQuestion(UUID.randomUUID().toString());
        question.setTitle(questionRequestDTO.getTitle());
        question.setCategory(categoryList.get(0).getIdCategory());
        question.setLevel(questionRequestDTO.getLevel());
        question.setAlternatives(questionRequestDTO.getAlternatives());
        question.setAnswer(questionRequestDTO.getAnswer());

        questionRepository.save(question);

        objResponse.setIdQuestion(question.getIdQuestion());
        objResponse.setTitle(question.getTitle());
        objResponse.setCategory(question.getCategory());
        objResponse.setLevel(question.getLevel());
        objResponse.setAlternatives(question.getAlternatives());
        objResponse.setAnswer(question.getAnswer());

        return objResponse;
    }

    public void updateQuestion(String idQuestion, QuestionRequestDTO questionRequestDTO) throws QuestionException {

            Optional<Question> question = questionRepository.findByIdQuestion(idQuestion);

            List<Category> categoryList = categoryRepository.findAllByDesc(questionRequestDTO.getCategory());

            if(!question.isPresent()){
                throw new QuestionException("Nenhuma pergunta encontrada com esse id: " + idQuestion);
            }

            question.get().setTitle(questionRequestDTO.getTitle());
            question.get().setCategory(categoryList.get(0).getIdCategory());
            question.get().setLevel(questionRequestDTO.getLevel());
            question.get().setAlternatives(questionRequestDTO.getAlternatives());
            question.get().setAnswer(questionRequestDTO.getAnswer());

            questionRepository.save(question.get());
    }

    public void deleteQuestionByIdQuestion(String idQuestion) throws QuestionException {

            Optional<Question> question = questionRepository.findByIdQuestion(idQuestion);

            if(!question.isPresent()){
                throw new QuestionException("Nenhuma pergunta encontrada com esse id: " + idQuestion);
            }

            questionRepository.delete(question.get());
    }

    public List<String> findAlternativesByIdQuestion(String idQuestion) throws QuestionException {
        Optional<Question> question = questionRepository.findByIdQuestion(idQuestion);

        if(!question.isPresent()){
            throw new QuestionException("Nenhuma pergunta encontrada com esse id: " + idQuestion);
        }

        return question.get().getAlternatives();
    }

    }
