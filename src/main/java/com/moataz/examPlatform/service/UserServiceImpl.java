package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    public AuthResponse register(UserDto userDto) {
        var user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .userType(userDto.getRole())
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getUserType().name())
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getUserType().name())
                .build();
    }

    @Override
    public String verify(User user) {
        String newToken = jwtService.generateToken(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getName());
        variables.put("activationLink", "https://yourapp.com/activate?token=" + newToken);

        emailService.sendEmailWithTemplate(
                user.getEmail(),
                "Welcome to Our Platform",
                "welcome-email",
                variables
        );
        return "Verify Email sent to your mail box";
    }

    @Override
    public String forgetPassword(ForgetRequest request) {
        User user= loadUserByUsername(request.getEmail());
        String newToken = jwtService.generateToken(loadUserByUsername(request.getEmail()));
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getName());
        variables.put("activationLink", "https://yourapp.com/activate?token=" + newToken);

        emailService.sendEmailWithTemplate(
                user.getEmail(),
                "Welcome to Our Platform",
                "welcome-email",
                variables
        );
        return "Reset Email sent to your mail box";
    }

    @Override
    public String isRightOwner(String token) {
        String email = jwtService.extractUsername(token);
        User user = repository.findByEmail(email).orElseThrow();
        if (user.isEnabled()){
            return "verified, now change password";
        }
        return null;
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        String email = jwtService.extractUsername(request.getToken());
        User user = repository.findByEmail(email).orElseThrow();
        if (user.isEnabled()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            repository.save(user);
            return "password Changed go to login";
        }
        throw new UsernameNotFoundException("email not found or user is disabled, contact admin");
    }

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getUserType())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    public User loadUserByUsername(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("no user with this email"));
    }
}
