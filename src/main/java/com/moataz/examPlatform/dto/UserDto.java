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
public class UserDto {
    private UUID userId;
    @NotBlank(message = "should type username")
    @Size(min = 4 , max = 50, message = "username should be between 4 to 50")
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Password must be at least 8 characters with 1 uppercase, 1 lowercase and 1 number")
    private String password;
    @NotBlank
    private Role role;

    private Boolean isVerified;

    private String image;

    @NotBlank(message = "Phone number is required")
    private String phone;
    @NotBlank(message = "City is required")
    private Location location;

    private List<Exam> createdExams;
    private Set<Subject> mySubjects;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}