package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.exception.UserIsNotAllowed;
import com.moataz.examPlatform.model.*;
import com.moataz.examPlatform.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ExamsServiceImpl implements ExamsService {
    private final ExamRepository examRepository;
    private final UserRepository repository;
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    private final QuestionAnswersRepository questionAnswersRepository;
    private final SubjectStudentRepository subjectStudentRepository;
    private final ExamsAttemptRepository examsAttemptRepository;

    @Override
    @Transactional
    public String createExam(ExamDto examDto, User user) {
        if (user.isEnabled()) {
            Exam exam = Exam.builder()
                    .title(examDto.getTitle())
                    .examType(ExamType.valueOf(examDto.getExamType()))
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
        if (isExist) {
            boolean isCreator = examRepository.isExamCreator(user.getUserId(), examDto.getExamId());
            if (isCreator) {
                var exam = examRepository.findById(examDto.getExamId());
                if (exam.isPresent()) {
                    Exam updatedExam = exam.get();
                    updatedExam.setTitle(examDto.getTitle());
                    updatedExam.setExamType(ExamType.valueOf(examDto.getExamType()));
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
        if (isExist) {
            boolean isCreator = examRepository.isExamCreator( user.getUserId() , UUID.fromString(examId));
            if (isCreator) {
                examRepository.deleteById(UUID.fromString(examId));
                return "deleted successfully";
            }
            throw new PermissionDeniedDataAccessException("User is not allowed to delete this exam", new UserIsNotAllowed());
        }
        throw new UserIsNotAllowed();
    }

    @Override
    public List<QuestionDto> getAllQuestionForExam(String id) {
        return questionRepository.findAllByExam_ExamId(UUID.fromString(id))
                .stream().map(Question::toQuestionDto)
                .toList();
    }

//    @Override
//    @Transactional
//    public String addQuestions(AddQuestionsRequest request) {
//        questionRepository.saveAll(request.getQuestions().stream().map(
//                questionDto -> Question.builder()
//                        .text(questionDto.getText())
//                        .marks(questionDto.getMarks())
//                        .build()
//        ).toList());
//
//        questionAnswersRepository.saveAll(
//                request.getQuestions().stream().flatMap(
//                        questionDto -> Stream.concat(questionDto.getRightAnswer().stream()
//                                .map(
//                                        answerDto -> QuestionAnswers.builder()
//                                                .question(Question.builder()
//                                                        .questionId(questionDto.getQuestionId())
//                                                        .build()
//                                                )
//                                                .text(answerDto)
//                                                .isCorrect(true)
//                                                .build()
//                                ),  questionDto.getWrongAnswer().stream()
//                                .map(
//                                        answerDto -> QuestionAnswers.builder()
//                                                .question(Question.builder()
//                                                        .questionId(questionDto.getQuestionId())
//                                                        .build()
//                                                )
//                                                .text(answerDto)
//                                                .isCorrect(false)
//                                                .build()
//                                ) )
//                ).toList()
//        );
//        return "Saved";
//    }

    @Override
    @Transactional
    public String addQuestions(AddQuestionsRequest request) {
        // 1. Validate input
        if (request == null || request.getQuestions() == null) {
            throw new IllegalArgumentException("Request cannot be null and must contain questions");
        }

        // 2. Save questions with exam association
        List<Question> savedQuestions = questionRepository.saveAll(
                request.getQuestions().stream()
                        .map(questionDto -> Question.builder()
                                .text(questionDto.getText())
                                .marks(questionDto.getMarks())
                                .type(questionDto.getType()) // Added question type
                                .exam(Exam.builder()
                                        .examId(UUID.fromString(request.getExamId())) // Associate with exam
                                        .build())
                                .build())
                        .toList()
        );

        // 3. Prepare answers with proper question references
        List<QuestionAnswers> answers = new ArrayList<>();

        for (int i = 0; i < savedQuestions.size(); i++) {
            Question question = savedQuestions.get(i);
            QuestionDto questionDto = request.getQuestions().get(i);

            // Add correct answers
            questionDto.getRightAnswer().forEach(answerText ->
                    answers.add(QuestionAnswers.builder()
                            .question(question) // Use the saved question reference
                            .text(answerText)
                            .isCorrect(true)
                            .build())
            );

            // Add wrong answers
            questionDto.getWrongAnswer().forEach(answerText ->
                    answers.add(QuestionAnswers.builder()
                            .question(question) // Use the saved question reference
                            .text(answerText)
                            .isCorrect(false)
                            .build())
            );
        }

        // 4. Save all answers
        questionAnswersRepository.saveAll(answers);

        return String.format("Saved %d questions with %d answers",
                savedQuestions.size(), answers.size());
    }

    @Override
    @Transactional
    public QuestionDto updateQuestions(List<UpdateQuestionRequest> questionDto) {
        QuestionDto questionDto1 = QuestionDto.builder().build();
        for (UpdateQuestionRequest dto : questionDto) {
            // 1. Update the question itself
            Question question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found: " + dto.getQuestionId()));

            questionDto1 = Question.toQuestionDto(question);
            question.setText(dto.getText());
            question.setMarks(dto.getMarks());
            question.setType(dto.getType()); // Don't forget to update the question type if needed
            questionRepository.save(question);

            // 2. First delete existing answers for this question
            questionAnswersRepository.deleteByQuestion(question);

            // 3. Prepare new answers
            List<QuestionAnswers> newAnswers = new ArrayList<>();

            // Add correct answers
            dto.getRightAnswer().forEach(answerDto ->
                    newAnswers.add(QuestionAnswers.builder()
                            .question(question) // Use the managed entity reference
                            .text(answerDto.getAnswer())
                            .isCorrect(true)
                            .build())
            );

            // Add wrong answers
            dto.getWrongAnswer().forEach(answerDto ->
                    newAnswers.add(QuestionAnswers.builder()
                            .question(question) // Use the managed entity reference
                            .text(answerDto.getAnswer())
                            .isCorrect(false)
                            .build())
            );

            // 4. Save all new answers
            questionAnswersRepository.saveAll(newAnswers);
        }
        return questionDto1;
    }

    @Override
    public String deleteQuestions(List<String> ids) {
        questionRepository.deleteAllById(ids.stream().map(UUID::fromString).toList());
        return "deleted";
    }

    @Override
    public List<ExamDto> getAllExamsToTeacher(User user) {
        return examRepository.findByCreatedByUserId(user.getUserId())
                .stream().map(Exam::toExamDto).toList();
    }

    @Override
    public StudentExams getAllExamsToStudent(User user) {
        List<Exam> studentExams = examRepository.findAll();

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
        return questionRepository.findAllByExam_ExamId(UUID.fromString(id))
                .stream().map(Question::toQuestionDto)
                .toList();
    }

    @Override
    public ExamDto getExamDto(String id) {
        Exam exam = examRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new ResourceNotFoundException("Exam not found with id: " + id)
        );
        ExamDto examDto = Exam.toExamDto(exam);
        examDto.setQuestionDto(questionRepository.findAllByExam_ExamId(UUID.fromString(id))
                .stream().map(Question::toQuestionDto)
                .toList());
        return Exam.toExamDto(exam);
    }

    @Override
    public List<UserStateDto> getAllUsersState() {
        List<UserStateDto> userStateDtos = new ArrayList<>();
        List<User> users = repository.findByUserType(Role.Student);
        for (User user : users) {
            var exams = examsAttemptRepository.findById_UserId(user.getUserId());
            userStateDtos.add(
                    UserStateDto.builder()
                            .username(user.getName())
                            .email(user.getEmail())
                            .phone(user.getPhone())
                            .activeExams(
                                    user.getUserSubjects()
                                            .stream()
                                            .map(userSubject -> userSubject.getSubject().getExams().stream().filter(
                                                            exam -> exam.getEndDate().isBefore(LocalDateTime.now())
                                                    ).count()
                                            ).reduce(Long::sum).orElse(0L)
                                            .intValue()
                            )
                            .attendedExams(exams.size())
                            .totalScore(exams.stream()
                                    .map(ExamAttempts::getScore)
                                    .reduce(Integer::sum).orElse(0))
                            .levelAndSemester(
                                    user.getUserSubjects().stream()
                                            .sorted(Comparator.comparing(o -> o.getSubject().getLevel() + o.getSubject().getSemester()))
                                            .map(userSubject -> {
                                                String level = String.valueOf(userSubject.getSubject().getLevel());
                                                String semester = String.valueOf(userSubject.getSubject().getSemester());
                                                return level + " - " + semester;
                                            }).findFirst().orElse("N/A")
                            )
                            .build()
            );
        }
        return userStateDtos;
    }

    @Override
    public String editUser(EditStudentRequest editStudentRequest, String studentId, User teacher) {
        User user = repository.findById(UUID.fromString(studentId)).orElseThrow(() -> new UsernameNotFoundException(""));
        if (user.getUserType() != Role.Student) {
            throw new UserIsNotAllowed();
        }
        user.setName(editStudentRequest.getName());
        user.setPhone(editStudentRequest.getPhoneNumber());

        for (String subject : editStudentRequest.getSubjects()) {
            Subject subjectEntity = subjectRepository.findById(UUID.fromString(subject))
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subject));
            UserSubject userSubject = UserSubject.builder()
                    .user(user)
                    .subject(subjectEntity)
                    .build();
            user.getUserSubjects().add(userSubject);
            repository.save(user);
            subjectStudentRepository.save(userSubject);
        }
        return "user updated successfully";
    }

    @Override
    public UserStateDetailedDto getUserStateByEmail(String email) {
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("no user with email"));
        List<ExamAttempts> attempts = examsAttemptRepository.findById_UserId(user.getUserId());
        List<Exam> originalExams = attempts.stream().map(et-> examRepository.findById(et.getId().getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("cant find exam with id" + et.getId().getExamId()))).collect(Collectors.toList());
        UserStateDetailedDto userStateDetailedDto = UserStateDetailedDto.builder()
                .name(user.getUsername())
                .email(user.getEmail())
                .totalScore(attempts.stream().map(ExamAttempts::getScore)
                        .reduce(Integer::sum).orElse(0))
                .exams_t(
                        originalExams.stream().map(attempt -> ExamDto.builder()
                                .ExamId(attempt.getExamId())
                                .title(attempt.getTitle())
                                .marks(originalExams.stream().filter(exam -> exam.getExamId().equals(attempt.getExamId()))
                                        .map(Exam::getMarks)
                                        .findFirst().orElse(0))
                                .startDate(attempt.getStartDate())
                                .endDate(attempt.getEndDate())
                                .build()
                        ).toList()
                ).build();
        return userStateDetailedDto;
    }

    @Override
    public String submitExamAnswers(String examId, List<StudentAnswers> answers, User user) {
        return null;
    }
}
