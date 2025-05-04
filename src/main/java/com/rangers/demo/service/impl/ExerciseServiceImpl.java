package com.rangers.demo.service.impl;

import com.rangers.demo.dto.ExerciseDto;
import com.rangers.demo.entity.Exercise;
import com.rangers.demo.service.ExerciseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        // реалізація
        return Page.empty(); // тимчасово
    }

    @Override
    public ExerciseDto getById(Long id) {
        // реалізація
        return null;
    }

    @Override
    @Transactional
    public ExerciseDto create(ExerciseDto dto) {
        Exercise ex = new Exercise();
        // Копируем совпадающие поля из dto → entity
        BeanUtils.copyProperties(dto, ex);
        if (ex.getId() == null) {
            throw new IllegalArgumentException("ID must be provided manually.");
        }
        // Если в DTO не передали dateAdded, ставим сегодня
        if (dto.getDateAdded() == null || dto.getDateAdded().isBlank()) {
            ex.setDateAdded(LocalDate.now().toString());
        }

        Exercise saved = repository.save(ex);

        // Возвращаем обратно DTO (скопируем из сущности)
        ExerciseDto out = new ExerciseDto();
        BeanUtils.copyProperties(saved, out);
        return out;
    }

    private ExerciseDto toDto(Exercise ex) {
        return ExerciseDto.builder()
                .id(ex.getId())
                .imageSrc(ex.getImageSrc())
                .title(ex.getTitle())
                .description(ex.getDescription())
                .type(ex.getType())
                .difficulty(ex.getDifficulty())
                .build();
    }
}
