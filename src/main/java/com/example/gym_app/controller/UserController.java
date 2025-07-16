package com.example.gym_app.controller;

import com.example.gym_app.dto.users.LoginRequest;
import com.example.gym_app.dto.users.RegisterRequest;
import com.example.gym_app.service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")

@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;

    // ======================================== Register ============================== //
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return userServices.create(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return userServices.login(request);
    }
}
