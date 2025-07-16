package com.example.gym_app.service;

import com.example.gym_app.dto.users.LoginRequest;
import com.example.gym_app.dto.users.LoginResponse;
import com.example.gym_app.dto.users.RegisterRequest;
import com.example.gym_app.dto.users.RegisterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface UserServices  {
    ResponseEntity<?> create(RegisterRequest request);
    ResponseEntity<?> login(LoginRequest request);
    ResponseEntity<?> update();
    ResponseEntity<?> delete();
    ResponseEntity<?> updateRole();
}
