package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.ExamDto;
import com.moataz.examPlatform.dto.QuestionDto;
import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.Question;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamsServiceImpl implements ExamsService {
    private final ExamRepository examRepository;

    @Override
    public List<ExamDto> getAllExamsToTeacher(User user) {
        return examRepository.findExamsByStudentId(user.getUserId())
                .stream().map(Exam::toExamDto).toList();
    }

    @Override
    public List<ExamDto> getAllExamsToStudent(User user) {
        return examRepository.findExamsByStudentId(user.getUserId())
                .stream().map(Exam::toExamDto).collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllExamQuestions(String id) {
        return examRepository.findById(UUID.fromString(id))
                .orElseThrow().getQuestions()
                .stream().map(Question::toQuestionDto)
                .toList();
    }
}
