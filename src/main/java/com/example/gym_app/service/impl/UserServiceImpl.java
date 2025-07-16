package com.example.gym_app.service.impl;

import com.example.gym_app.config.JWTService;
import com.example.gym_app.dto.users.LoginRequest;
import com.example.gym_app.dto.users.LoginResponse;
import com.example.gym_app.dto.users.RegisterRequest;
import com.example.gym_app.dto.users.RegisterResponse;
import com.example.gym_app.model.UserPrincipal;
import com.example.gym_app.model.Users;
import com.example.gym_app.repository.UserRepo;
import com.example.gym_app.service.UserServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServices {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    @Override
    public ResponseEntity<?> create(RegisterRequest request) {
        Optional<Users> findExistingUsers = userRepo.findByEmail(request.getEmail());
        if(findExistingUsers.isPresent()){
            return ResponseEntity.status(403).body("User email already registered. Try another email");
        }

        Users newUser = RegisterRequest.toEntity(request);

        newUser.setPassword(passwordEncoder.encode(request.getPassword()));


        Users saved = userRepo.save(newUser);

        UserDetails userDetails = new UserPrincipal(saved);

        RegisterResponse response = RegisterResponse.toDTO(saved);
        String token = jwtService.generateToken(userDetails);
        response.setToken(token);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        // dto to entity
        Users loginUser = LoginRequest.toEntity(request);
       // loginUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // find user
        Optional<Users> findUser = userRepo.findByEmail(loginUser.getEmail());
        if(findUser.isEmpty()){
            return ResponseEntity.status(404).body("Invalid User email address");
        }
        System.out.println("Password matcher " + loginUser.getPassword() + " -> " + findUser.get().getPassword());
        if(passwordEncoder.matches(request.getPassword(), findUser.get().getPassword())){
            Users users = findUser.get();
            UserDetails userDetails = new UserPrincipal(users);
            String token = jwtService.generateToken(userDetails);

            LoginResponse response = LoginResponse.toDTO(users);
            response.setToken(token);

            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }

    @Override
    public ResponseEntity<?> update() {
        return null;
    }

    @Override
    public ResponseEntity<?> delete() {
        return null;
    }

    @Override
    public ResponseEntity<?> updateRole() {
        return null;
    }
}
