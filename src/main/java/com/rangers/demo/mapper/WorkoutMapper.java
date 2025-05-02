package com.rangers.demo.mapper;

import com.rangers.demo.dto.WorkoutDto;
import com.rangers.demo.dto.WorkoutExerciseDto;
import com.rangers.demo.entity.Workout;
import com.rangers.demo.entity.WorkoutExercise;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkoutMapper {

    @Mapping(target = "exercises", source = "exercises")
    WorkoutDto toDto(Workout workout);

    @Mapping(target = "user", ignore = true)
    Workout toEntity(WorkoutDto workoutDto);

    WorkoutExerciseDto toExerciseDto(WorkoutExercise exercise);

    @Mapping(target = "workout", ignore = true)
    @Mapping(target = "id", ignore = true)
    WorkoutExercise toExerciseEntity(WorkoutExerciseDto exerciseDto);

    List<WorkoutExerciseDto> toExerciseDtoList(List<WorkoutExercise> exercises);

    List<WorkoutExercise> toExerciseEntityList(List<WorkoutExerciseDto> exerciseDtos);
}