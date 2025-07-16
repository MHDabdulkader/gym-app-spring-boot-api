package com.example.gym_app.service;

import com.example.gym_app.config.JWTService;
import com.example.gym_app.model.Users;
import com.example.gym_app.utility.ErrorResponseUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

class JWTServiceTest {
    private JWTService jwtService;
    private UserDetails testUsers;
    @Value("${jwt.secret}")
    private String secret_key;

    @BeforeEach
    void SetUp(){
        byte[] keyBytes = Base64.getDecoder().decode(secret_key.getBytes(StandardCharsets.UTF_8));

        ErrorResponseUtil.logMsg("Secret key", secret_key);
        String key = new SecretKeySpec(keyBytes,"HmacSHA256").toString();
        ErrorResponseUtil.logMsg("key", key);
        // set field on service
        ReflectionTestUtils.setField(jwtService,"secret_key", key);
        testUsers = (UserDetails) Users.builder()
                .email("test@gmail.com")
                .password("1234")
                .roles(List.of("USERS"))
                .build();
    }

    @Test
    void Generate_Valid_Token_JWT(){
        String token = jwtService.generateToken(testUsers);

        Assertions
                .assertNotNull(token);
    }
}
