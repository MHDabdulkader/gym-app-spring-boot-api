package com.example.gym_app.dto.users;

import com.example.gym_app.model.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String email;
    private String password;

    public static Users toEntity(LoginRequest dto){
        return Users.builder()
                .email(dto.getEmail())
                .build();
    }
}
