package com.rangers.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDto {
    /**
     * Unique identifier for the workout record
     */
    private UUID id;

    /**
     * Date of the workout in ISO format (e.g., 2025-05-01)
     */
    private String date;

    /**
     * List of exercises performed during this workout
     */
    private List<WorkoutExerciseDto> exercises;
    public WorkoutDto(String date, List<WorkoutExerciseDto> exercises) {
        this.date = date;
        this.exercises = exercises;
    }
}
