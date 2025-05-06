package com.rangers.demo.service.impl;

import com.rangers.demo.dto.*;
import com.rangers.demo.entity.User;
import com.rangers.demo.entity.Workout;
import com.rangers.demo.mapper.UserMapper;
import com.rangers.demo.mapper.WorkoutMapper;
import com.rangers.demo.repository.UserRepository;
import com.rangers.demo.repository.WorkoutRepository;
import com.rangers.demo.security.jwt.JwtService;
import com.rangers.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final UserMapper userMapper;
    private final WorkoutMapper workoutMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    @Override
    @Transactional
    public UserDto getUserById(String id) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findByUserId(UUID.fromString(id))
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public String getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> user.getUserId().toString())
                .orElse(null); // Return null instead of throwing exception
    }
    @Override
    public Map<String, Object> addUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return Map.of(
                "message", "User added successfully",
                "userId", savedUser.getUserId().toString()
        );
    }

    @Override
    @Transactional
    public List<WorkoutDto> getWorkouts(UUID userId) {
        List<Workout> workouts = workoutRepository.findByUserUserId(userId);
        return workouts.stream()
                .map(workoutMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public WorkoutDto addWorkout(UUID userId, WorkoutDto workoutDto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Workout workout = workoutMapper.toEntity(workoutDto);
        workout.setUser(user);


        if (workout.getDate() == null || workout.getDate().isBlank()) {
            workout.setDate(LocalDate.now().toString()); // результат типу "2025-05-02"
        }

        // ВАЖЛИВО: встановити workout у кожній вправі
        if (workout.getExercises() != null) {
            workout.getExercises().forEach(exercise -> exercise.setWorkout(workout));
        }

        Workout saved = workoutRepository.save(workout);
        return workoutMapper.toDto(saved);
    }

    private User findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    private User findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception(String.format("User with email %s not found", email)));
    }

    @Override
    @Transactional
    public UserDto updateUser(String id, UserDto userDto) throws ChangeSetPersister.NotFoundException {
        // Получаем пользователя из БД по ID
        User user = userRepository.findByUserId(UUID.fromString(id))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Обновляем generalInformation если предоставлено
        if (userDto.getGeneralInformation() != null) {
            UserDto.GeneralInformation generalInfo = userDto.getGeneralInformation();

            if (generalInfo.getEmail() != null) {
                user.setEmail(generalInfo.getEmail());
            }

            if (generalInfo.getGender() != null) {
                user.setGender(generalInfo.getGender());
            }

            if (generalInfo.getGoal() != null) {
                user.setGoal(generalInfo.getGoal());
            }
        }

        // Обновляем metrics если предоставлено
        if (userDto.getMetrics() != null) {
            UserDto.Metrics metrics = userDto.getMetrics();

            if (metrics.getAge() != null) {
                user.setAge(metrics.getAge());
            }

            if (metrics.getHeight() != null) {
                user.setHeight(metrics.getHeight());
            }

            if (metrics.getWeight() != null) {
                user.setWeight(metrics.getWeight());
            }

            if (metrics.getPhysicalActivityLevel() != null) {
                user.setPhysicalActivityLevel(metrics.getPhysicalActivityLevel());
            }
        }

        // Если в DTO присутствует пароль и он не пустой, обновляем его
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Сохраняем обновленного пользователя
        User savedUser = userRepository.save(user);

        // Используем маппер для конвертации сущности в DTO
        return userMapper.toDto(savedUser);
    }
}