package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;

import java.util.List;

public interface ExamsService {
    String createExam(ExamDto examDto, User user);
    ExamDto updateExam(ExamDto examDto, User user);
    String deleteExam(String examId, User user);

    List<QuestionDto> getAllQuestionForExam(String id);
    String addQuestions(AddQuestionsRequest request);
    QuestionDto updateQuestions(List<UpdateQuestionRequest> questionDto);
    String deleteQuestions(List<String> ids);

    List<ExamDto> getAllExamsToTeacher(User user);
    StudentExams getAllExamsToStudent(User user);
    List<QuestionDto> getAllExamQuestions(String id);
    ExamDto getExamDto(String id);
    List<UserStateDto> getAllUsersState();
    UserStateDetailedDto getUserStateByEmail(String email);

    String editUser(EditStudentRequest editStudentRequest, String studentId, User teacher);

    String submitExamAnswers(String examId, List<StudentAnswers> answers, User user);
}
