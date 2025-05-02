package com.rangers.demo.service;

import com.rangers.demo.dto.ExerciseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface ExerciseService {
    Page<ExerciseDto> list(Pageable pageable, Map<String, String> filters);
    ExerciseDto getById(UUID id);
}
