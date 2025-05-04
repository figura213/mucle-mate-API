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
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final UserService userService;
   // @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/sign-up")
    public Map<String, String> createUser(@RequestBody UserDto userDto) {
        String result = userService.addUser(userDto);
        return Map.of("message", result);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.singIn(userCredentialsDto);
            return ResponseEntity.ok(Map.of("token", jwtAuthenticationDto));
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
