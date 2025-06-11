package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    AuthResponse register(UserDto userDto, HttpSession session);
    AuthResponse login(AuthRequest request, HttpSession session);

    String verify(User user);

    String forgetPassword(ForgetRequest request);

    String isRightOwner(String token);

    User loadUserByUsername(String email);

    String resetPassword(ResetPasswordRequest request);
}
