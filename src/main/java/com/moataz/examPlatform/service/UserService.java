package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    User getUserProfile(String email);
    UserDto convertToDto(User user);
    User loadUserByUsername(String email);
    String updateMyImg(MultipartFile image, String phone);
    String deleteUser(String userId);

    User updateProfile(User me, UpdateProfileRequest request);

    User updateProfileImage(User me, MultipartFile image);
}