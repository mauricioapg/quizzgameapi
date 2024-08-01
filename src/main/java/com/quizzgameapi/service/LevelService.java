package com.quizzgameapi.service;

import com.quizzgameapi.dto.LevelRequestDTO;
import com.quizzgameapi.dto.LevelResponseDTO;
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

    public LevelResponseDTO findByIdLevel(String idLevel) throws Exception {
        Optional<Level> level = levelRepository.findByIdLevel(idLevel);

        if(!level.isPresent()){
            throw new Exception("Nenhum nível encontrado com esse id: " + idLevel);
        }

        LevelResponseDTO objResponse = new LevelResponseDTO();
        objResponse.setIdLevel(level.get().getIdLevel());
        objResponse.setDesc(level.get().getDesc());

        return objResponse;
    }

    public LevelResponseDTO findByDesc(String desc) throws Exception {
        Optional<Level> level = levelRepository.findByDesc(desc);

        if(!level.isPresent()){
            throw new Exception("Nenhum nível encontrado com essa descrição: " + desc);
        }

        LevelResponseDTO objResponse = new LevelResponseDTO();
        objResponse.setIdLevel(level.get().getIdLevel());
        objResponse.setDesc(level.get().getDesc());

        return objResponse;
    }

    public Level createLevel(LevelRequestDTO levelRequestDTO) throws Exception{

        Level level = new Level();

        level.setIdLevel(UUID.randomUUID().toString());
        level.setDesc(levelRequestDTO.getDesc());

        List<Level> levelsFound = levelRepository.findAllByDesc(level.getDesc());

        if(levelsFound.isEmpty()){
            levelRepository.save(level);
        }
        else{
            throw new Exception("Já existe um nível com essa descrição: " + levelRequestDTO.getDesc());
        }

        return level;
    }

    public void updateLevel(String idLevel, LevelRequestDTO levelRequestDTO) throws Exception {

        Optional<Level> level = levelRepository.findByIdLevel(idLevel);

        if(!level.isPresent()){
            throw new Exception("Nenhum nível encontrado com esse id: " + idLevel);
        }

        level.get().setDesc(levelRequestDTO.getDesc());

        levelRepository.save(level.get());
    }

    public void deleteLevelByIdLevel(String idLevel) throws Exception {

        Optional<Level> level = levelRepository.findByIdLevel(idLevel);

        if(!level.isPresent()){
            throw new Exception("Nenhum nível encontrado com esse id: " + idLevel);
        }

        levelRepository.delete(level.get());
    }

}
