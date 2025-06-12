package com.moataz.examPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStateDetailedDto {
    private String name;
    private String email;
    private Integer totalScore;
    private List<ExamDto> exams_t;
}
