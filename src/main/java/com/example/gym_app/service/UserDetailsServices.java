package com.example.gym_app.service;

import com.example.gym_app.model.UserPrincipal;
import com.example.gym_app.model.Users;
import com.example.gym_app.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServices implements UserDetailsService {
    private final UserRepo userRepo;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> users = userRepo.findByEmail(email);
        if(users.isEmpty()){
            throw new UsernameNotFoundException("User not founded"+email);
        }
        return new UserPrincipal(users.get());
    }
}
