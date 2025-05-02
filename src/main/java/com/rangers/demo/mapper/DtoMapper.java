package com.rangers.demo.mapper;

import com.rangers.demo.dto.UserDto;
import com.rangers.demo.dto.WorkoutDto;
import com.rangers.demo.entity.User;
import com.rangers.demo.entity.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {
    UserDto userToDto(User user);
    User userToEntity(UserDto userDto);

    WorkoutDto workoutToDto(Workout workout);
    Workout workoutToEntity(WorkoutDto workoutDto);
}
