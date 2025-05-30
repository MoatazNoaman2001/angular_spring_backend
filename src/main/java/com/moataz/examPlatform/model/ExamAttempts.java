package com.moataz.examPlatform.model;


import ch.qos.logback.core.util.Loader;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exams_attempts")
public class ExamAttempts {
    @EmbeddedId
    private ExamsAttemptId id;

    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime EndDate;

    private Integer score;

    private Integer Total;
}
