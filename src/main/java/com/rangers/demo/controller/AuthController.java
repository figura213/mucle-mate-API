package com.rangers.demo.controller;

import com.rangers.demo.dto.JwtAuthenticationDto;
import com.rangers.demo.dto.RefreshTokenDto;
import com.rangers.demo.dto.UserCredentialsDto;
import com.rangers.demo.dto.UserDto;
import com.rangers.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDto userDto) {
        Map<String, Object> result = userService.addUser(userDto);

        // After user creation, generate authentication token
        try {
            UserCredentialsDto credentials = new UserCredentialsDto(
                    userDto.getEmail(),
                    userDto.getPassword()
            );

            JwtAuthenticationDto jwtAuthenticationDto = userService.singIn(credentials);

            // Create response with both user ID and token
            Map<String, Object> response = new HashMap<>();
            response.put("userId", result.get("userId"));
            response.put("auth", jwtAuthenticationDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AuthenticationException e) {
            // In case token generation fails, still return the user ID
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }
    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.singIn(userCredentialsDto);

            // Get user ID based on the email
            String userId = userService.getUserIdByEmail(userCredentialsDto.getEmail());

            // Create response with both user ID and token
            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("auth", jwtAuthenticationDto);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/refresh")
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return userService.refreshToken(refreshTokenDto);
    }
}