package com.moataz.examPlatform.controller;

import com.moataz.examPlatform.dto.StudentExams;
import com.moataz.examPlatform.dto.UserStateDto;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.service.ExamsService;
import com.moataz.examPlatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ExamsService examsService;

    @PatchMapping("/set-image")
    private ResponseEntity<String> setProfileImage(@RequestPart MultipartFile image, Authentication authentication){
        var user = ((User) authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.updateMyImg(image, user.getPhone()));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserStateDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsersState());
    }

    @GetMapping("/exams")
    public ResponseEntity<StudentExams> getAllStudentExams(Authentication authentication){
        var user = ((User)authentication.getPrincipal());
        return ResponseEntity.ok(examsService.getAllExamsToStudent(user));
    }
}
