package com.quizzgameapi.service;

import com.quizzgameapi.dto.LevelRequestDTO;
import com.quizzgameapi.exception.LevelException;
import com.quizzgameapi.model.Level;
import com.quizzgameapi.repository.LevelRepository;
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
class LevelServiceTests {

	Level level;

	LevelRequestDTO levelRequestDTO;

	@Mock
	LevelRepository levelRepository;

	@InjectMocks
	LevelService levelService;

	@BeforeEach
	public void setUp(){

		levelRequestDTO = new LevelRequestDTO();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void validateLevelDuplicateException(){

		levelRequestDTO.setDesc("iniciante");

		// Configura o mock para retornar uma lista não vazia indicando que a categoria já existe
		when(levelRepository.findAllByDesc("iniciante")).thenReturn(List.of(new Level()));

		// Verifica se a exceção CategoryException é lançada
		LevelException thrownException = assertThrows(
				LevelException.class,
				() -> levelService.createLevel(levelRequestDTO),
				"Já existe um nível com essa descrição: "
		);

		// Verifica a mensagem da exceção
		assertEquals("Já existe um nível com essa descrição: iniciante", thrownException.getMessage());

	}

	@Test
	void validateLevelNotFoundException() throws LevelException{

		level = new Level();
		level.setIdLevel("1");

		// Verifica se a exceção CategoryException é lançada
		LevelException thrownException = assertThrows(
				LevelException.class,
				() -> levelService.findByIdLevel(level.getIdLevel()),
				"Nenhum nível encontrado com esse id: "
		);

		// Verifica a mensagem da exceção
		assertEquals("Nenhum nível encontrado com esse id: 1", thrownException.getMessage());

	}

	@Test
	void createLevel() throws LevelException {
		level = new Level();
		level.setIdLevel(UUID.randomUUID().toString());
		level.setDesc(levelRequestDTO.getDesc());

		when(levelRepository.save(level)).thenReturn(level);

		var result = levelService.createLevel(levelRequestDTO);

		//Verificando retorno do método createLevel
		Assertions.assertEquals(level.getDesc(), result.getDesc());
	}

	@Test
	void updateLevel() {
		// Configura o mock para retornar um nível existente
		String idLevel = "75293f40-6925-4a3e-ab2b-d5cb9f00279c";

		level = new Level();
		level.setIdLevel(idLevel);
		level.setDesc("iniciante");

		levelRequestDTO.setDesc("iniciante Novo");

		when(levelRepository.findByIdLevel(idLevel)).thenReturn(Optional.of(level));

		// Chama o método
		levelService.updateLevel(idLevel, levelRequestDTO);

		// Verifica se a descrição do nível foi atualizado corretamente
		assertEquals("iniciante Novo", level.getDesc());

		// Verifica se o repositório save foi chamado com o nível atualizado
		verify(levelRepository, times(1)).save(level);
	}

	@Test
	void deleteLevel() {
		// Configura o mock para retornar um nível existente
		String idLevel = "dcd575dd-6a57-47e7-ae6e-83e759a7f7d2";

		level = new Level();
		level.setIdLevel(idLevel);

		when(levelRepository.findByIdLevel(idLevel)).thenReturn(Optional.of(level));

		// Chama o método
		levelService.deleteLevelByIdLevel(idLevel);

		// Verifica se o repositório delete foi chamado com o nível
		verify(levelRepository, times(1)).delete(level);
	}

}
