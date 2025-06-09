package com.moataz.examPlatform.controller;

import com.moataz.examPlatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StaticsController {
    private final AuthService authService;

    @GetMapping("/activate")
    public ResponseEntity<String> activate(@RequestParam(value = "token") String token) {
        return ResponseEntity.ok(authService.isRightOwner(token));
    }
}
