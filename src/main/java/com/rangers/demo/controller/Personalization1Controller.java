package com.rangers.demo.controller;

import com.rangers.demo.dto.UserDto;
import com.rangers.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/personalization/1")
@RequiredArgsConstructor
@CrossOrigin
public class Personalization1Controller {

    private final UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getPersonalization1(@PathVariable String userId)
            throws ChangeSetPersister.NotFoundException {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(Map.of("data", user.getGeneralInformation()));
    }


    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updatePersonalization1(
            @PathVariable String userId,
            @RequestBody UserDto.GeneralInformation generalInfo)
            throws ChangeSetPersister.NotFoundException {

        UserDto user = userService.getUserById(userId);

        // Update only gender and goal fields
        UserDto.GeneralInformation currentInfo = user.getGeneralInformation();
        if (currentInfo == null) {
            currentInfo = new UserDto.GeneralInformation();
            user.setGeneralInformation(currentInfo);
        }

        currentInfo.setGender(generalInfo.getGender());
        currentInfo.setGoal(generalInfo.getGoal());

        // Preserve email and username
        if (currentInfo.getEmail() == null) {
            currentInfo.setEmail(user.getEmail());
        }
        if (currentInfo.getUserName() == null) {
            currentInfo.setUserName(user.getFirstName());
        }

        UserDto updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(Map.of("data", updatedUser.getGeneralInformation()));
    }
}