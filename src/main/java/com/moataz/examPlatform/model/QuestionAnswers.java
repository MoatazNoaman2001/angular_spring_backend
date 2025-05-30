package com.moataz.examPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question_answers")
public class QuestionAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID answerId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String text;

    @Column(name = "is_correct")
    private Boolean isCorrect;
}
