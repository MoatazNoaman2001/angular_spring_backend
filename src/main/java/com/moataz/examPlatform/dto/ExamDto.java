package com.moataz.examPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private UUID ExamId;
    private String title;
    private String description;
    private Duration duration;
    private Integer marks;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
