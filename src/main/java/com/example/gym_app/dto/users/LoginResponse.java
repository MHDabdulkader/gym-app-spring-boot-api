package com.example.gym_app.dto.users;

import com.example.gym_app.model.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String email;
    private String token;

    public static LoginResponse toDTO(Users users){
        return LoginResponse.builder()
                .email(users.getEmail())
                .build();
    }
}
