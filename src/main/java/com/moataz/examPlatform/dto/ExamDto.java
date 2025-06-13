package com.moataz.examPlatform.dto;

import com.moataz.examPlatform.model.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private UUID ExamId;
    private String subject;
    @NotBlank(message = "must add exam title")
    private String title;
    private String examType;
    private Integer marks;
    private List<QuestionDto> questionDto;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    private String duration;
//    public Duration getDuration() {
//        return Duration.between(startDate, endDate);
//    }
}
