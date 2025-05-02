package com.rangers.demo.service;

import com.rangers.demo.dto.JwtAuthenticationDto;
import com.rangers.demo.dto.RefreshTokenDto;
import com.rangers.demo.dto.UserCredentialsDto;
import com.rangers.demo.dto.UserDto;
import org.springframework.data.crossstore.ChangeSetPersister;
import com.rangers.demo.dto.WorkoutDto;
import java.util.List;
import java.util.UUID;
import javax.naming.AuthenticationException;

public interface UserService {
    JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;
    UserDto getUserById(String id) throws ChangeSetPersister.NotFoundException;
    UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException;
    String addUser(UserDto user);
    List<WorkoutDto> getWorkouts(UUID userId);
    WorkoutDto addWorkout(UUID userId, WorkoutDto workoutDto);
    UserDto updateUser(String id, UserDto userDto) throws ChangeSetPersister.NotFoundException;

}
