package com.rangers.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredentialsDto {
    private String email;
    private String password;
}
