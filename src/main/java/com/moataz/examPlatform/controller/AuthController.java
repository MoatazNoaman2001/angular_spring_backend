package com.moataz.examPlatform.controller;
import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDto request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(Authentication authentication){
        User user =(User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.verify(user));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetRequest request){
        return ResponseEntity.ok(userService.forgetPassword(request));
    }

    @GetMapping("/check-account")
    public ResponseEntity<String> checkAccount(@RequestPart String token){
        return ResponseEntity.ok(userService.isRightOwner(token));
    }


    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return ResponseEntity.ok(userService.resetPassword(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}