package com.moataz.examPlatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moataz.examPlatform.dto.ExamDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DurationFormatUtils;
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
    private ExamType examType;

    private Duration duration;
    private Integer marks;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User createdBy;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();


    public static ExamDto  toExamDto(Exam exam){
        return ExamDto.builder()
                .ExamId(exam.examId)
                .title(exam.title)
                .examType(exam.examType.name())
                .marks(exam.marks)
                .startDate(exam.startDate)
                .endDate(exam.endDate)
//                .questionDto(exam.questions)
                .duration(
                        DurationFormatUtils.formatDuration(exam.duration.toMillis(), "H:mm:ss", true)
                )
                .build();

    }
}
