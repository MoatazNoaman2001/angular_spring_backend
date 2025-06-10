package com.moataz.examPlatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditStudentRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 8)
    private String phoneNumber;
    private List<String> subjects= new ArrayList<>();

}
