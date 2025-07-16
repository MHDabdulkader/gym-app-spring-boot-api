package com.example.gym_app.dto.users;

import com.example.gym_app.model.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String email;
    private String token;

    public static RegisterResponse toDTO(Users users){
        return RegisterResponse.builder()
                .email(users.getEmail())
                .build();
    }
}
