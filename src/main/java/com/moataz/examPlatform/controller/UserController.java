package com.moataz.examPlatform.controller;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.service.ExamsService;
import com.moataz.examPlatform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
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

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.convertToDto(user));
    }
    @PatchMapping("/profile")
    public ResponseEntity<UpdateProfileResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        User me = ((User) authentication.getPrincipal());
        UpdateProfileResponse updatedUser = userService.updateProfile(me, request);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/profile/image")
    public ResponseEntity<UserDto> updateProfileImage(
            Authentication authentication,
            @RequestPart("image") MultipartFile image
    ) {
        User me = ((User) authentication.getPrincipal());
        User updatedUser = userService.updateProfileImage(me, image);
        return ResponseEntity.ok(userService.convertToDto(updatedUser));
    }

    @GetMapping("/exams")
    public ResponseEntity<StudentExams> getAllStudentExams(Authentication authentication){
        var user = ((User)authentication.getPrincipal());
        return ResponseEntity.ok(examsService.getAllExamsToStudent(user));
    }

    @GetMapping("/exams/{examId}")
    public ResponseEntity<ExamDto> getExamById(@PathVariable("examId") String examId , Authentication authentication){
        var user = ((User)authentication.getPrincipal());
        ExamDto examDto = examsService.getExamDto(examId);
        return ResponseEntity.ok(examDto);
    }
    @PostMapping("/answers/{examId}")
    public ResponseEntity<String> submitExamAnswers(@PathVariable String examId, @RequestBody List<StudentAnswers> answers, Authentication authentication) {
        var user = ((User) authentication.getPrincipal());
        return ResponseEntity.ok(examsService.submitExamAnswers(examId, answers, user));
    }

}
