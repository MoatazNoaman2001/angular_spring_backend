package com.moataz.examPlatform.controller;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.GetUserRequest;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.service.ExamsService;
import com.moataz.examPlatform.service.TeacherService;
import com.moataz.examPlatform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final ExamsService examsService;
    private final UserService userService;
    private final TeacherService teacherService;

    @GetMapping("/dashboard")
    public ResponseEntity<TeacherDashboardDto> getTeacherDashboard(Authentication authentication) {
        return ResponseEntity.ok(teacherService.getTeacherDashboard((User) authentication.getPrincipal()));
    }

    @PostMapping("/exams")
    public ResponseEntity<String> createExam(@Valid @RequestBody ExamDto examDto, Authentication authentication){
        return ResponseEntity.ok(examsService.createExam(examDto, (User) authentication.getPrincipal()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserStateDto>> getAllUsers() {
        return ResponseEntity.ok(examsService.getAllUsersState());
    }

    @GetMapping("/users/get-user-by-email")
    public ResponseEntity<User> getUserByEmail(@RequestBody GetUserRequest request){
        return ResponseEntity.ok(userService.loadUserByUsername(request.getEmail()));
    }

    @PutMapping("/edit-user/{userId}")
    public ResponseEntity<String> editUser(@Valid @RequestBody EditStudentRequest editStudentRequest,@PathVariable("userId") String studentId, Authentication authentication) {
        return ResponseEntity.ok(examsService.editUser(editStudentRequest, studentId, (User) authentication.getPrincipal()));
    }

    @DeleteMapping("/users")
    public ResponseEntity<String>deleteUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.deleteUser(email));
    }


    @GetMapping("/exams")
    public ResponseEntity<List<ExamDto>> getAllTeacherExam(Authentication authentication){
        return ResponseEntity.ok(examsService.getAllExamsToTeacher((User) authentication.getPrincipal()));
    }

    @PutMapping("/exams")
    public ResponseEntity<ExamDto> updateExam(@Valid @RequestBody ExamDto examDto, Authentication authentication){
        return ResponseEntity.ok(examsService.updateExam(examDto, (User) authentication.getPrincipal()));
    }

    @DeleteMapping("/exams/{examId}")
    public ResponseEntity<String> deleteExam(@RequestPart(value = "examId") String examId , Authentication authentication){
        return ResponseEntity.ok(examsService.deleteExam(examId , (User) authentication.getPrincipal()));
    }

    @PostMapping("/questions")
    public ResponseEntity<String> addQuestions(@Valid @RequestBody AddQuestionsRequest questionsRequest, Authentication authentication){
        return ResponseEntity.ok(examsService.addQuestions(questionsRequest));
    }

    @PutMapping("/questions")
    public ResponseEntity<String> updateQuestions(@Valid @RequestBody List<UpdateQuestionRequest> questions){
        return ResponseEntity.ok(examsService.updateQuestions(questions));
    }

    @DeleteMapping("/questions")
    public ResponseEntity<String> deleteExam(@Valid @RequestBody List<String> questionIds, Authentication authentication){
        return ResponseEntity.ok(examsService.deleteQuestions(questionIds));
    }
}
