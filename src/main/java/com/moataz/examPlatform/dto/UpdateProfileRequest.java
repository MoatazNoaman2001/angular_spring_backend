package com.moataz.examPlatform.dto;

import com.moataz.examPlatform.model.Location;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfileRequest {
    @NotBlank(message = "should type name")
    private String name;
    @Size(min = 8, message = "phone number should be at least 8 characters")
    private String phoneNumber;
    @Email
    private String email;

    private Location Address;

}
