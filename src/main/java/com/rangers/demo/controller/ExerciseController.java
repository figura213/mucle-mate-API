
package com.rangers.demo.controller;

import com.rangers.demo.dto.ExerciseDto;
import com.rangers.demo.service.ExerciseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/exercises")
@CrossOrigin
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }
    // конструктор

    @GetMapping
    public ResponseEntity<Map<String, Object>> listExercises(
            @RequestParam Optional<String> type,
            @RequestParam Optional<String> q,
            @RequestParam Optional<String> difficulty,
            @RequestParam Optional<String> duration,
            @RequestParam Optional<String> id,
            @RequestParam Optional<String> target,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int limit
    ) {
        Pageable pg = PageRequest.of(page, limit);
        Map<String,String> filters = new HashMap<>();
        type.ifPresent(v -> filters.put("type", v));
        q.ifPresent(v -> filters.put("q", v));
        id.ifPresent(v -> filters.put("id", v));
        difficulty.ifPresent(v -> filters.put("difficulty", v));
        duration.ifPresent(v -> filters.put("duration", v));
        target.ifPresent(v -> filters.put("target", v));

        Page<ExerciseDto> result = exerciseService.list(pg, filters);
        return ResponseEntity.ok(Map.of("data", result));
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> getExercise(@PathVariable Long id) {
        ExerciseDto dto = exerciseService.getById(id);
        return ResponseEntity.ok(Map.of("data", dto));
    }
    @PostMapping("/bulk")
    public ResponseEntity<Void> uploadBulk(
            @RequestBody List<ExerciseDto> items
    ) {
        for (ExerciseDto it : items) {
            // в DTO могут прийти и другие поля, но мы заполним только нужные
            ExerciseDto dto = ExerciseDto.builder()
                    .id(it.getId())  // ← ОБЯЗАТЕЛЬНО
                    .imageSrc(it.getImageSrc())
                    .title(it.getTitle())
                    .description(it.getDescription())
                    .guide(it.getGuide())
                    .duration(it.getDuration())
                    .type(it.getType())
                    .difficulty(it.getDifficulty())
                    .targetMuscleGroups(it.getTargetMuscleGroups())
                    .dateAdded(it.getDateAdded())
                    .build();

            exerciseService.create(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
