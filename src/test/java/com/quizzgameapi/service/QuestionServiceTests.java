package com.quizzgameapi.service;

import com.quizzgameapi.dto.QuestionRequestDTO;
import com.quizzgameapi.exception.QuestionException;
import com.quizzgameapi.model.Category;
import com.quizzgameapi.model.Question;
import com.quizzgameapi.repository.CategoryRepository;
import com.quizzgameapi.repository.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class QuestionServiceTests {

	Category category;

	Question question;

	QuestionRequestDTO questionRequestDTO;

	@Mock
	QuestionRepository questionRepository;

	@Mock
	CategoryRepository categoryRepository;

	@InjectMocks
	QuestionService questionService;

	@BeforeEach
	public void setUp(){
		questionRequestDTO = new QuestionRequestDTO();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void validateQuestionNotFoundException() throws QuestionException {

		question = new Question();
		question.setIdQuestion("1");

		// Verifica se a exceção QuestionException é lançada
		QuestionException thrownException = assertThrows(
				QuestionException.class,
				() -> questionService.findByIdQuestion(question.getIdQuestion()),
				"Nenhuma pergunta encontrada com esse id: "
		);

		// Verifica a mensagem da exceção
		assertEquals("Nenhuma pergunta encontrada com esse id: 1", thrownException.getMessage());

	}

	@Test
	void createQuestion() throws QuestionException {

		category = new Category();
		category.setIdCategory("f1a7d5c6-a9e6-47dc-ac4a-636cac80b1d3");
		category.setDesc("Geografia");

		questionRequestDTO.setCategory("Geografia");

		var categoryList = new ArrayList<Category>();
		categoryList.add(category);

		when(categoryRepository.findAllByDesc("Geografia")).thenReturn(List.of(new Category()));

		question = new Question();
		question.setIdQuestion(UUID.randomUUID().toString());
		question.setTitle(questionRequestDTO.getTitle());
		question.setCategory(categoryList.get(0).getIdCategory());
		question.setLevel(questionRequestDTO.getLevel());
		question.setAnswer(questionRequestDTO.getAnswer());
		question.setAlternatives(questionRequestDTO.getAlternatives());

		when(questionRepository.save(question)).thenReturn(question);

		var result = questionService.createQuestion(questionRequestDTO);

		//Verificando retorno do método createQuestion
		Assertions.assertEquals(question.getTitle(), result.getTitle());
	}

	@Test
	void updateQuestion() {
		// Configura o mock para retornar uma pergunta existente
		String idQuestion = "dd27ff3d-c9d0-422d-b864-3aa124d21b25";

		category = new Category();
		category.setIdCategory("f1a7d5c6-a9e6-47dc-ac4a-636cac80b1d3");
		category.setDesc("Geografia");

		var categoryList = new ArrayList<Category>();
		categoryList.add(category);

		when(categoryRepository.findAllByDesc("Geografia")).thenReturn(List.of(new Category()));

		question = new Question();
		question.setIdQuestion(idQuestion);
		question.setTitle("Qual a capital do estado de Roraima?");
		question.setCategory(categoryList.get(0).getIdCategory());
		question.setLevel("iniciante");
		question.setAlternatives(List.of("Boa Vista", "Porto Velho", "Rio Branco", "Fortaleza"));
		question.setAnswer("Boa Vista");

		questionRequestDTO.setTitle("Teste Novo");
		questionRequestDTO.setCategory("Geografia");
		questionRequestDTO.setLevel("2");
		questionRequestDTO.setAlternatives(List.of("teste2", "teste3", "teste4", "teste5"));
		questionRequestDTO.setAnswer("teste2");

		when(questionRepository.findByIdQuestion(idQuestion)).thenReturn(Optional.of(question));

		// Chama o método
		questionService.updateQuestion(idQuestion, questionRequestDTO);

		// Verifica se o título da pergunta foi atualizado corretamente
		assertEquals("Teste Novo", question.getTitle());

		// Verifica se o repositório save foi chamado com a pergunta atualizada
		verify(questionRepository, times(1)).save(question);
	}

	@Test
	void deleteQuestion() {
		// Configura o mock para retornar uma pergunta existente
		String idQuestion = "dcd575dd-6a57-47e7-ae6e-83e759a7f7d2";

		question = new Question();
		question.setIdQuestion(idQuestion);

		when(questionRepository.findByIdQuestion(idQuestion)).thenReturn(Optional.of(question));

		// Chama o método
		questionService.deleteQuestionByIdQuestion(idQuestion);

		// Verifica se o repositório delete foi chamado com a pergunta
		verify(questionRepository, times(1)).delete(question);
	}

}
