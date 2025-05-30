package com.moataz.examPlatform.model;

import com.moataz.examPlatform.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID questionId;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private String text;


    private Integer marks;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionAnswers> answers = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static QuestionDto toQuestionDto(Question question){
        return QuestionDto.builder()
                .questionId(question.questionId)
                .createdAt(question.createdAt)
                .marks(question.marks)
                .text(question.text)
                .type(question.type)
                .updatedAt(question.updatedAt)
                .build();
    }
}
