package com.rangers.demo.controller;

import com.rangers.demo.dto.UserDto;
import com.rangers.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/personalization/1")
@RequiredArgsConstructor
public class Personalization1Controller {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto.GeneralInformation> getPersonalization1(@PathVariable String userId)
            throws ChangeSetPersister.NotFoundException {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user.getGeneralInformation());
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto.GeneralInformation> updatePersonalization1(
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
        return ResponseEntity.ok(updatedUser.getGeneralInformation());
    }
}