package com.moataz.examPlatform.model;

import com.moataz.examPlatform.dto.ExamDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID examId;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String title;
    private String description;

    private Duration duration;
    private Integer marks;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();


    public static ExamDto toExamDto(Exam exam){
        return ExamDto.builder()
                .ExamId(exam.examId)
                .title(exam.title)
                .description(exam.description)
                .duration(exam.duration)
                .marks(exam.marks)
                .startDate(exam.startDate)
                .endDate(exam.endDate)
                .build();

    }
}
