package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;

import java.util.List;

public interface ExamsService {
    String createExam(ExamDto examDto, User user);
    ExamDto updateExam(ExamDto examDto, User user);
    String deleteExam(String examId, User user);

    String addQuestions(AddQuestionsRequest request);
    String updateQuestions(List<QuestionDto> questionDto);
    String deleteQuestions(List<String> ids);

    List<ExamDto> getAllExamsToTeacher(User user);
    StudentExams getAllExamsToStudent(User user);
    List<QuestionDto> getAllExamQuestions(String id);
    List<UserStateDto> getAllUsersState();

    String editUser(EditStudentRequest editStudentRequest, String studentId, User teacher);
}
