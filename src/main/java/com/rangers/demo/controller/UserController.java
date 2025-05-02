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
import java.util.UUID;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) throws ChangeSetPersister.NotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) throws ChangeSetPersister.NotFoundException {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{id}/workouts")
    public List<WorkoutDto> getUserWorkouts(@PathVariable UUID id) {
        return userService.getWorkouts(id);
    }
  @PostMapping("/{id}/workout")
   public WorkoutDto addWorkout(@PathVariable UUID id, @RequestBody WorkoutDto workoutDto) {
      System.out.println("Received date: " + workoutDto.getDate());

      return userService.addWorkout(id, workoutDto);

    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @Valid @RequestBody UserDto userDto)
            throws ChangeSetPersister.NotFoundException {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }


}
