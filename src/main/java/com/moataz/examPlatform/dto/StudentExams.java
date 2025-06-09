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
public class StudentExams {
    private List<ExamDto> pastExams;
    private List<ExamDto> currentExams;
    private List<ExamDto> upComingExams;
}
