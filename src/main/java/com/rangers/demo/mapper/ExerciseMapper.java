package com.rangers.demo.mapper;

import com.rangers.demo.dto.ExerciseDto;
import com.rangers.demo.entity.Exercise;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseMapper {

    @Mapping(target = "target", expression = "java(exercise.getTarget() != null ? exercise.getTarget().toArray(new String[0]) : null)")
    ExerciseDto toDto(Exercise exercise);

    @Mapping(target = "target", expression = "java(exerciseDto.getTarget() != null ? java.util.Arrays.asList(exerciseDto.getTarget()) : null)")
    @Mapping(target = "equipment", ignore = true)
    @Mapping(target = "mediaLinks", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Exercise toEntity(ExerciseDto exerciseDto);
}