package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.ExamDto;
import com.moataz.examPlatform.dto.QuestionDto;
import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.Question;
import com.moataz.examPlatform.model.User;

import java.util.List;

public interface ExamsService {


    List<ExamDto> getAllExamsToTeacher(User user);
    List<ExamDto> getAllExamsToStudent(User user);
    List<QuestionDto> getAllExamQuestions(String id);
}
