package com.moataz.examPlatform.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "must add exam title")
    private String title;
    private String description;
    private Integer marks;
    @NotBlank(message = "add start date")
    private LocalDateTime startDate;
    @NotBlank(message = "add end date")
    private LocalDateTime endDate;
    private Duration duration = Duration.between(startDate, endDate);
}
