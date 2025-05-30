package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;

import java.util.List;


public interface UserService {

    AuthResponse register(UserDto userDto);
    AuthResponse login(AuthRequest request);

    String verify(User user);

    String forgetPassword(ForgetRequest request);

    List<UserDto> getAllUsers();

    UserDto convertToDto(User user);

    User loadUserByUsername(String email);

    String isRightOwner(String token);

    String resetPassword(ResetPasswordRequest request);
}