package com.moataz.examPlatform.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "must send question")
    List<QuestionDto> questions;
    @NotBlank(
        message = "must send exam id"
    )
    String examId;
}
