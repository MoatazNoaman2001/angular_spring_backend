package com.moataz.examPlatform.dto;

import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.Location;
import com.moataz.examPlatform.model.Role;
import com.moataz.examPlatform.model.Subject;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentRequest {

    @NotBlank(message = "should type username")
    @Size(min = 4 , max = 50, message = "username should be between 4 to 50")
    private String name;
    @Email
    private String email;

    private String password = "User1234";

    private Role role = Role.Student;

    private Boolean isVerified = false;

    private String image;

    @Size( min = 10 , message = "Phone number is required")
    private String phone;
}