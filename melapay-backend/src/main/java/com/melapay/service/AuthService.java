package com.melapay.service;

import com.melapay.dto.LoginRequest;
import com.melapay.dto.RegisterRequest;
import com.melapay.entity.User;
import com.melapay.repository.UserRepository;
import com.melapay.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) throw new RuntimeException("Email exists");

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        userRepository.save(user);

        return jwtService.generateToken(user.getEmail());
    }

    public String login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return jwtService.generateToken(user.getEmail());
    }
}
