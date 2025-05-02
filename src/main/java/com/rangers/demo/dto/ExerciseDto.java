package com.rangers.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

/**
 * DTO для публичного упражнения
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDto {
    /**
     * Уникальный идентификатор упражнения
     */
    private UUID id;

    /**
     * URL или путь к изображению упражнения
     */
    private String imageSrc;

    /**
     * Название упражнения
     */
    private String title;

    /**
     * Краткое описание назначения упражнения
     */
    private String description;

    /**
     * Пошаговое руководство или советы по выполнению
     */
    private String guide;

    /**
     * Ориентировочная продолжительность выполнения (например, "00:30:00" или "10 min")
     */
    private String duration;

    /**
     * Тип упражнения (например, "strength", "cardio")
     */
    private String type;

    /**
     * Уровень сложности (например, "beginner", "intermediate", "advanced")
     */
    private String difficulty;

    /**
     * Список целевых групп мышц (массив строк, например, ["back", "biceps"])
     */
    private String[] target;

    /**
     * Дата добавления упражнения в формате ISO-строки (например, "2025-05-01")
     */
    private String dateAdded;
}