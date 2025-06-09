package com.moataz.examPlatform.controller;

import com.moataz.examPlatform.dto.AddQuestionsRequest;
import com.moataz.examPlatform.dto.ExamDto;
import com.moataz.examPlatform.dto.QuestionDto;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.service.ExamsService;
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
    @PostMapping("/exams")
    public ResponseEntity<String> createExam(@Valid @RequestBody ExamDto examDto, Authentication authentication){
        return ResponseEntity.ok(examsService.createExam(examDto, (User) authentication.getPrincipal()));
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
    public ResponseEntity<String> updateQuestions(@Valid @RequestBody List<QuestionDto> questions){
        return ResponseEntity.ok(examsService.updateQuestions(questions));
    }

    @DeleteMapping("/questions")
    public ResponseEntity<String> deleteExam(@Valid @RequestBody List<String> questionIds, Authentication authentication){
        return ResponseEntity.ok(examsService.deleteQuestions(questionIds));
    }
}
