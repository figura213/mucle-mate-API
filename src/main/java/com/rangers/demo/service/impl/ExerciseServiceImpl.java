package com.rangers.demo.service.impl;

import com.rangers.demo.dto.ExerciseDto;
import com.rangers.demo.service.ExerciseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Override
    public Page<ExerciseDto> list(Pageable pageable, Map<String, String> filters) {
        // реалізація
        return Page.empty(); // тимчасово
    }

    @Override
    public ExerciseDto getById(UUID id) {
        // реалізація
        return null;
    }
}
