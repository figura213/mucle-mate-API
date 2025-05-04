package com.rangers.demo.security.jwt;

import com.rangers.demo.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticate(org.springframework.security.core.Authentication authentication) {
        String token = (String) authentication.getCredentials();
        if (jwtService.validateJwtToken(token)) {
            String email = jwtService.getEmailFromToken(token);  // Отримуємо email з токена
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");  // Тільки приклад ролі
            return new UsernamePasswordAuthenticationToken(email, token, Collections.singletonList(authority));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
