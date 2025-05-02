package com.rangers.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO для отдельного упражнения внутри тренировки
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseDto {
    /**
     * Название упражнения (например, "Squat" или "Bench Press")
     */
    private String title;

    /**
     * Количество повторений в подходе
     */
    private Integer reps;

    /**
     * Количество подходов
     */
    private Integer sets;

    /**
     * Используемый вес (в кг)
     */
    private Double weight;

}
