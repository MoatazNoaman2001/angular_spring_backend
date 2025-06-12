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
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getTeacherProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getMyProfile(user));
    }

    @PatchMapping("/profile")
    public ResponseEntity<UpdateProfileResponse> updateTeacherProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateTeacherProfileRequest request
    ) {
        User me = ((User) authentication.getPrincipal());
        UpdateProfileResponse updatedUser = teacherService.updateProfile(me, request);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/student/add")
    public ResponseEntity<UserDto> addStudent(@Valid @RequestBody AddStudentRequest userDto){
        return ResponseEntity.ok(teacherService.addStudent(userDto));
    }

    @PutMapping("/profile/image")
    public ResponseEntity<UserDto> updateTeacherProfileImage(
            Authentication authentication,
            @RequestPart("image") MultipartFile image
    ) {
        User me = ((User) authentication.getPrincipal());
        User updatedUser = userService.updateProfileImage(me, image);
        return ResponseEntity.ok(userService.convertToDto(updatedUser));
    }

    @PostMapping("/exams")
    public ResponseEntity<String> createExam(@Valid @RequestBody ExamDto examDto, Authentication authentication){
        return ResponseEntity.ok(examsService.createExam(examDto, (User) authentication.getPrincipal()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserStateDto>> getAllUsers() {
        return ResponseEntity.ok(examsService.getAllUsersState());
    }

    @GetMapping("/users/info")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.convertToDto(userService.getUserProfile(email)));
    }

    @GetMapping("/users/result")
    public ResponseEntity<UserStateDetailedDto> getStudentResult(@RequestParam String email, Authentication authentication) {
        return ResponseEntity.ok(examsService.getUserStateByEmail(email));
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
    public ResponseEntity<String> deleteExam(@PathVariable(value = "examId") String examId , Authentication authentication){
        return ResponseEntity.ok(examsService.deleteExam(examId , (User) authentication.getPrincipal()));
    }

    @GetMapping("/questions/{examId}")
    public ResponseEntity<List<QuestionDto>> getAllExamQuestions(@PathVariable("examId")  String id){
        return ResponseEntity.ok(examsService.getAllExamQuestions(id));
    }

    @PostMapping("/questions")
    public ResponseEntity<String> addQuestions(@Valid @RequestBody AddQuestionsRequest questionsRequest, Authentication authentication){
        return ResponseEntity.ok(examsService.addQuestions(questionsRequest));
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestions(@PathVariable("questionId") String qid, @Valid @RequestBody UpdateQuestionRequest question){
        return ResponseEntity.ok(examsService.updateQuestions(List.of(question)));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable(value = "questionId", required = true) String qid, Authentication authentication){
        return ResponseEntity.ok(examsService.deleteQuestions(List.of(qid)));
    }
}
