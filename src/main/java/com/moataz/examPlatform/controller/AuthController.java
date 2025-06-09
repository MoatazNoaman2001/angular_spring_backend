package com.moataz.examPlatform.controller;
import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request, HttpSession session) {
        return ResponseEntity.ok(authService.register(request, session));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpSession session) {
        return ResponseEntity.ok(authService.login(request, session));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(Authentication authentication){
        User user =(User) authentication.getPrincipal();
        return ResponseEntity.ok(authService.verify(user));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetRequest request){
        return ResponseEntity.ok(authService.forgetPassword(request));
    }

    @GetMapping("/check-account")
    public ResponseEntity<String> checkAccount(@RequestPart String token){
        return ResponseEntity.ok(authService.isRightOwner(token));
    }


    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return ResponseEntity.ok(authService.resetPassword(request));
    }

}