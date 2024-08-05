package com.quizzgameapi.service;

import com.quizzgameapi.dto.LevelRequestDTO;
import com.quizzgameapi.dto.LevelResponseDTO;
import com.quizzgameapi.exception.LevelException;
import com.quizzgameapi.model.Level;
import com.quizzgameapi.repository.LevelRepository;
import com.quizzgameapi.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<LevelResponseDTO> listAllLevels(){
        List<Level> levels = levelRepository.findAll();
        List<LevelResponseDTO> listLevelResponseDTO = new ArrayList<>();

        levels.forEach((level -> {
            LevelResponseDTO obj = new LevelResponseDTO();
            obj.setIdLevel(level.getIdLevel());
            obj.setDesc(level.getDesc());

            listLevelResponseDTO.add(obj);
        }));

        return listLevelResponseDTO;
    }

    public LevelResponseDTO findByIdLevel(String idLevel) throws LevelException {
        Optional<Level> level = levelRepository.findByIdLevel(idLevel);

        if(!level.isPresent()){
            throw new LevelException("Nenhum nível encontrado com esse id: " + idLevel);
        }

        LevelResponseDTO objResponse = new LevelResponseDTO();
        objResponse.setIdLevel(level.get().getIdLevel());
        objResponse.setDesc(level.get().getDesc());

        return objResponse;
    }

    public LevelResponseDTO findByDesc(String desc) throws LevelException {
        Optional<Level> level = levelRepository.findByDesc(desc);

        if(!level.isPresent()){
            throw new LevelException("Nenhum nível encontrado com essa descrição: " + desc);
        }

        LevelResponseDTO objResponse = new LevelResponseDTO();
        objResponse.setIdLevel(level.get().getIdLevel());
        objResponse.setDesc(level.get().getDesc());

        return objResponse;
    }

    public Level createLevel(LevelRequestDTO levelRequestDTO) throws LevelException{

        Level level = new Level();

        level.setIdLevel(UUID.randomUUID().toString());
        level.setDesc(levelRequestDTO.getDesc());

        List<Level> levelsFound = levelRepository.findAllByDesc(level.getDesc());

        if(!levelsFound.isEmpty()){
            throw new LevelException("Já existe um nível com essa descrição: " + levelRequestDTO.getDesc());
        }

        levelRepository.save(level);

        return level;
    }

    public void updateLevel(String idLevel, LevelRequestDTO levelRequestDTO) throws LevelException {

        Optional<Level> level = levelRepository.findByIdLevel(idLevel);

        if(!level.isPresent()){
            throw new LevelException("Nenhum nível encontrado com esse id: " + idLevel);
        }

        level.get().setDesc(levelRequestDTO.getDesc());

        levelRepository.save(level.get());
    }

    public void deleteLevelByIdLevel(String idLevel) throws LevelException {

        Optional<Level> level = levelRepository.findByIdLevel(idLevel);

        if(!level.isPresent()){
            throw new LevelException("Nenhum nível encontrado com esse id: " + idLevel);
        }

        levelRepository.delete(level.get());
    }

}
