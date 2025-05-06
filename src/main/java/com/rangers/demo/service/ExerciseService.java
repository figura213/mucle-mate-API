package com.rangers.demo.service;

import com.rangers.demo.dto.ExerciseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ExerciseService {
    Page<ExerciseDto> list(Pageable pageable, Map<String, String> filters);
    ExerciseDto getById(Long id);

    ExerciseDto create(ExerciseDto exerciseDto);
    Page<ExerciseDto> listAll(Pageable pageable);
}
