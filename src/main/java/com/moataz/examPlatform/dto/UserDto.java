package com.moataz.examPlatform.dto;

import com.moataz.examPlatform.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto{
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}