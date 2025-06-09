package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.AddQuestionsRequest;
import com.moataz.examPlatform.dto.ExamDto;
import com.moataz.examPlatform.dto.QuestionDto;
import com.moataz.examPlatform.dto.StudentExams;
import com.moataz.examPlatform.exception.UserIsNotAllowed;
import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.Question;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamsServiceImpl implements ExamsService {
    private final ExamRepository examRepository;

    @Override
    public String createExam(ExamDto examDto, User user) {
        if (user.isEnabled()) {
            Exam exam = Exam.builder()
                    .examId(UUID.randomUUID())
                    .title(examDto.getTitle())
                    .description(examDto.getDescription())
                    .marks(examDto.getMarks())
                    .startDate(examDto.getStartDate())
                    .endDate(examDto.getEndDate())
                    .duration(examDto.getDuration())
                    .createdBy(user)
                    .build();
            examRepository.save(exam);
            return "saved successfully";
        }
        return "user currently disabled";
    }

    @Override
    public ExamDto updateExam(ExamDto examDto, User user) {
        boolean isExist = examRepository.findById(examDto.getExamId()).isPresent();
        if (isExist){
            boolean isCreator= examRepository.isExamCreator(examDto.getExamId(), user.getUserId());
            if (isCreator){
                var exam = examRepository.findById(examDto.getExamId());
                if (exam.isPresent()) {
                    Exam updatedExam = exam.get();
                    updatedExam.setTitle(examDto.getTitle());
                    updatedExam.setDescription(examDto.getDescription());
                    updatedExam.setMarks(examDto.getMarks());
                    updatedExam.setStartDate(examDto.getStartDate());
                    updatedExam.setEndDate(examDto.getEndDate());
                    updatedExam.setDuration(examDto.getDuration());
                    examRepository.save(updatedExam);
                    return Exam.toExamDto(updatedExam);
                }
                throw new ResourceNotFoundException();
            }
            throw new PermissionDeniedDataAccessException("User is not allowed to update this exam", new UserIsNotAllowed());
        }
        throw new UserIsNotAllowed();
    }

    @Override
    public String deleteExam(String examId, User user) {
        boolean isExist = examRepository.findById(UUID.fromString(examId)).isPresent();
        if (isExist){
            boolean isCreator= examRepository.isExamCreator(UUID.fromString(examId), user.getUserId());
            if (isCreator){
                examRepository.deleteById(UUID.fromString(examId));
                return "deleted successfully";
            }
            throw new PermissionDeniedDataAccessException("User is not allowed to update this exam", new UserIsNotAllowed());
        }
        throw new UserIsNotAllowed();
    }

    @Override
    public String addQuestions(AddQuestionsRequest request) {
        return null;
    }

    @Override
    public String updateQuestions(List<QuestionDto> questionDto) {
        return null;
    }

    @Override
    public String deleteQuestions(List<String> ids) {
        return null;
    }

    @Override
    public List<ExamDto> getAllExamsToTeacher(User user) {
        return examRepository.findExamsByStudentId(user.getUserId())
                .stream().map(Exam::toExamDto).toList();
    }

    @Override
    public StudentExams getAllExamsToStudent(User user) {
        List<Exam> studentExams = examRepository.findExamsByStudentId(user.getUserId());

        List<ExamDto> pastExams = new ArrayList<>();
        List<ExamDto> currentExams = new ArrayList<>();
        List<ExamDto> upComingExams = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(23, 59, 59, 999999999);

        for (Exam exam : studentExams) {
            ExamDto examDto = Exam.toExamDto(exam);
            if (exam.getEndDate().isBefore(startOfToday)) {
                pastExams.add(examDto);
            } else if (!exam.getStartDate().isAfter(endOfToday) && !exam.getEndDate().isBefore(startOfToday)) {
                currentExams.add(examDto);
            } else if (exam.getStartDate().isAfter(endOfToday)) {
                upComingExams.add(examDto);
            }
        }

        return StudentExams.builder()
                .pastExams(pastExams)
                .currentExams(currentExams)
                .upComingExams(upComingExams)
                .build();
    }

    @Override
    public List<QuestionDto> getAllExamQuestions(String id) {
        return examRepository.findById(UUID.fromString(id))
                .orElseThrow().getQuestions()
                .stream().map(Question::toQuestionDto)
                .toList();
    }
}
