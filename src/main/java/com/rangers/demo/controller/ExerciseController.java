
package com.rangers.demo.controller;

import com.rangers.demo.dto.ExerciseDto;
import com.rangers.demo.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }
    // конструктор

    @GetMapping
    public ResponseEntity<Page<ExerciseDto>> listExercises(
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
        Map<String,String> filters = new HashMap<>();type.ifPresent(v -> filters.put("type", v));
        q.ifPresent(v -> filters.put("q", v));
        id.ifPresent(v -> filters.put("id", v));
        difficulty.ifPresent(v -> filters.put("difficulty", v));
        duration.ifPresent(v -> filters.put("duration", v));
        target.ifPresent(v -> filters.put("target", v));
        Page<ExerciseDto> result = exerciseService.list(pg, filters);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getExercise(@PathVariable UUID id) {
        return ResponseEntity.ok(exerciseService.getById(id));
    }
}
