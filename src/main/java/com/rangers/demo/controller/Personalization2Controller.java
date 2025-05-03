package com.rangers.demo.controller;

import com.rangers.demo.dto.UserDto;
import com.rangers.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/personalization/2")
@RequiredArgsConstructor
public class Personalization2Controller {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto.Metrics> getPersonalization2(@PathVariable String userId)
            throws ChangeSetPersister.NotFoundException {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user.getMetrics());
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto.Metrics> updatePersonalization2(
            @PathVariable String userId,
            @RequestBody UserDto.Metrics metrics)
            throws ChangeSetPersister.NotFoundException {

        UserDto user = userService.getUserById(userId);

        // Update metrics
        user.setMetrics(metrics);

        UserDto updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser.getMetrics());
    }
}