package com.moataz.examPlatform.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamsAttemptId implements Serializable {
    private UUID userId;
    private UUID subjectId;
    private UUID ExamId;
}
