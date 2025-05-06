package com.rangers.demo.controller;
import com.rangers.demo.entity.Workout;
import com.rangers.demo.dto.UserDto;
import com.rangers.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rangers.demo.dto.WorkoutDto;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String id) throws ChangeSetPersister.NotFoundException {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(Map.of("data", user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@PathVariable String email) throws ChangeSetPersister.NotFoundException {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(Map.of("data", user));
    }
    @GetMapping("/{id}/workouts")
    public ResponseEntity<Map<String, Object>> getUserWorkouts(@PathVariable UUID id) {
        List<WorkoutDto> workouts = userService.getWorkouts(id);
        return ResponseEntity.ok(Map.of("data", workouts));
    }
  @PostMapping("/{id}/workout")
  public ResponseEntity<Map<String, Object>> addWorkout(@PathVariable UUID id, @RequestBody WorkoutDto workoutDto) {
      System.out.println("Received date: " + workoutDto.getDate());

      WorkoutDto createdWorkout = userService.addWorkout(id, workoutDto);
      return ResponseEntity.status(201).body(Map.of("data", createdWorkout));
  }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable String id, @Valid @RequestBody UserDto userDto)
            throws ChangeSetPersister.NotFoundException {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(Map.of("data", updatedUser));
    }

}
