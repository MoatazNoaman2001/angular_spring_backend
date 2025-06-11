package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.ExamDto;
import com.moataz.examPlatform.dto.StudentExams;
import com.moataz.examPlatform.dto.TeacherDashboardDto;
import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.ExamAttempts;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.model.UserSubject;
import com.moataz.examPlatform.repository.ExamRepository;
import com.moataz.examPlatform.repository.ExamsAttemptRepository;
import com.moataz.examPlatform.repository.SubjectStudentRepository;
import com.moataz.examPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final UserRepository userRepository;
    private final SubjectStudentRepository subjectStudentRepository;
    private final ExamRepository examRepository;
    private final ExamsAttemptRepository examsAttemptRepository;

    @Override
    public TeacherDashboardDto getTeacherDashboard(User user) {
        List<Exam> studentExams = examRepository.findByCreatedByUserId(user.getUserId());

        List<ExamDto> pastExams = new ArrayList<>();
        List<ExamDto> currentExams = new ArrayList<>();
        List<ExamDto> upComingExams = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        List<ExamAttempts> currentAttempts = new ArrayList<>();
//        LocalDate today = LocalDate.now();
//        LocalDateTime startOfToday = today.atStartOfDay();
//        LocalDateTime endOfToday = today.atTime(23, 59, 59, 999999999);

//        List<String> myStudents = studentExams.
        for (Exam exam : studentExams) {
            ExamDto examDto = Exam.toExamDto(exam);
            if (exam.getEndDate().isBefore(now)) {
                pastExams.add(examDto);
            } else if (!exam.getStartDate().isAfter(now) && !exam.getEndDate().isBefore(now)) {
//                var attempt = examsAttemptRepository.findById_ExamId(exam.getExamId());
//                if (attempt.isPresent()){
//                    currentAttempts.add(attempt.get());
//                }else{
//                    throw new ResourceNotFoundException("error gather exam");
//                }
                currentExams.add(examDto);
            } else if (exam.getStartDate().isAfter(now)) {
                upComingExams.add(examDto);
            }
        }

        return null;
    }
}
