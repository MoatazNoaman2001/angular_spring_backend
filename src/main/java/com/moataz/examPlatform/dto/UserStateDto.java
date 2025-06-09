package com.moataz.examPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStateDto {

    private String username;
    private String email;
    private String phone;
    private Integer attendedExams;
    private Integer activeExams;
    private Integer totalScore;
    private String levelAndSemester;

}
