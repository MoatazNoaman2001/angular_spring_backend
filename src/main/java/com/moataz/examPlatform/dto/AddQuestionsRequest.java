package com.moataz.examPlatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddQuestionsRequest {

    @NotNull
    List<QuestionDto> questions;
    @NotBlank(
        message = "must send exam id"
    )
    String examId;
}
