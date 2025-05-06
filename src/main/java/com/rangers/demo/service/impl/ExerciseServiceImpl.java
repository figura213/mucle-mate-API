package com.rangers.demo.service.impl;

import com.rangers.demo.dto.ExerciseDto;
import com.rangers.demo.entity.Exercise;
import com.rangers.demo.service.ExerciseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.rangers.demo.repository.ExerciseRepository;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository repository;

    public ExerciseServiceImpl(ExerciseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<ExerciseDto> list(Pageable pageable, Map<String, String> filters) {
        Specification<Exercise> spec = createSpecification(filters);
        Page<Exercise> exercisePage = repository.findAll(spec, pageable);
        return exercisePage.map(this::toDto);
    }

    private Specification<Exercise> createSpecification(Map<String, String> filters) {
        Specification<Exercise> spec = Specification.where(null);

        if (filters.containsKey("type") && !filters.get("type").isEmpty()) {
            String type = filters.get("type");
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("type"), type));
        }

        if (filters.containsKey("q") && !filters.get("q").isEmpty()) {
            String searchTerm = "%" + filters.get("q") + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("title"), searchTerm));
        }

        if (filters.containsKey("id") && !filters.get("id").isEmpty()) {
            try {
                Long id = Long.parseLong(filters.get("id"));
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id));
            } catch (NumberFormatException e) {
                // If ID is not a number, try string comparison (UUID or other format)
                String id = filters.get("id");
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id").as(String.class), id));
            }
        }

        if (filters.containsKey("difficulty") && !filters.get("difficulty").isEmpty()) {
            String difficulty = filters.get("difficulty");
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("difficulty"), difficulty));
        }

        if (filters.containsKey("duration") && !filters.get("duration").isEmpty()) {
            String duration = filters.get("duration");
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("duration"), duration));
        }

        if (filters.containsKey("target") && !filters.get("target").isEmpty()) {
            String target = filters.get("target");
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("targetMuscleGroup"), target));
        }

        return spec;
    }

    @Override
    public ExerciseDto getById(Long id) {
        Exercise exercise = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + id));
        return toDto(exercise);
    }

    @Override
    @Transactional
    public ExerciseDto create(ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        // Copy matching properties from dto to entity
        BeanUtils.copyProperties(exerciseDto, exercise);

        if (exercise.getId() == null) {
            throw new IllegalArgumentException("ID must be provided manually.");
        }

        // Set today's date if dateAdded is not provided
        if (exerciseDto.getDateAdded() == null || exerciseDto.getDateAdded().isBlank()) {
            exercise.setDateAdded(LocalDate.now().toString());
        }

        Exercise saved = repository.save(exercise);

        // Convert back to DTO
        return toDto(saved);
    }

    // Additional utility methods

    private ExerciseDto toDto(Exercise exercise) {
        ExerciseDto dto = new ExerciseDto();
        BeanUtils.copyProperties(exercise, dto);
        return dto;
    }
}