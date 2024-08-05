package com.quizzgameapi.service;

import com.quizzgameapi.dto.CategoryRequestDTO;
import com.quizzgameapi.exception.CategoryException;
import com.quizzgameapi.model.Category;
import com.quizzgameapi.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTests {

	Category category;

	CategoryRequestDTO categoryRequestDTO;

	@Mock
	CategoryRepository categoryRepository;

	@InjectMocks
	CategoryService categoryService;

	@BeforeEach
	public void setUp(){

		categoryRequestDTO = new CategoryRequestDTO();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void validateCategoryDuplicateException(){

		categoryRequestDTO.setDesc("Games");

		// Configura o mock para retornar uma lista não vazia indicando que a categoria já existe
		when(categoryRepository.findAllByDesc("Games")).thenReturn(List.of(new Category()));

		// Verifica se a exceção CategoryException é lançada
		CategoryException thrownException = assertThrows(
				CategoryException.class,
				() -> categoryService.createCategory(categoryRequestDTO),
				"Já existe uma categoria com essa descrição: "
		);

		// Verifica a mensagem da exceção
		assertEquals("Já existe uma categoria com essa descrição: Games", thrownException.getMessage());

	}

	@Test
	void validateCategoryNotFoundException() throws CategoryException{

		category = new Category();
		category.setIdCategory("1");

		// Verifica se a exceção CategoryException é lançada
		CategoryException thrownException = assertThrows(
				CategoryException.class,
				() -> categoryService.findByIdCategory(category.getIdCategory()),
				"Nenhuma categoria encontrada com esse id: "
		);

		// Verifica a mensagem da exceção
		assertEquals("Nenhuma categoria encontrada com esse id: 1", thrownException.getMessage());

	}

	@Test
	void createCategory() throws CategoryException {
		category = new Category();
		category.setIdCategory(UUID.randomUUID().toString());
		category.setDesc(categoryRequestDTO.getDesc());

		when(categoryRepository.save(category)).thenReturn(category);

		var result = categoryService.createCategory(categoryRequestDTO);

		//Verificando retorno do método createCategory
		Assertions.assertEquals(category.getDesc(), result.getDesc());
	}

	@Test
	void updateCategory() {
		// Configura o mock para retornar uma categoria existente
		String idCategory = "dcd575dd-6a57-47e7-ae6e-83e759a7f7d2";

		category = new Category();
		category.setIdCategory(idCategory);
		category.setDesc("Esportes");

		categoryRequestDTO.setDesc("Esportes Novo");

		when(categoryRepository.findByIdCategory(idCategory)).thenReturn(Optional.of(category));

		// Chama o método
		categoryService.updateCategory(idCategory, categoryRequestDTO);

		// Verifica se a descrição da categoria foi atualizada corretamente
		assertEquals("Esportes Novo", category.getDesc());

		// Verifica se o repositório save foi chamado com a categoria atualizada
		verify(categoryRepository, times(1)).save(category);
	}

	@Test
	void deleteCategory() {
		// Configura o mock para retornar uma categoria existente
		String idCategory = "dcd575dd-6a57-47e7-ae6e-83e759a7f7d2";

		category = new Category();
		category.setIdCategory(idCategory);

		when(categoryRepository.findByIdCategory(idCategory)).thenReturn(Optional.of(category));

		// Chama o método
		categoryService.deleteCategoryByIdCategory(idCategory);

		// Verifica se o repositório delete foi chamado com a categoria
		verify(categoryRepository, times(1)).delete(category);
	}

}
