package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {
    List<UserStateDto> getAllUsersState();

    User getUserProfile(String email);

//    String updateUserProfile(UserDto userDto, String email);
 
    RegisterRequest convertToDto(User user);

    User loadUserByUsername(String email);
    String updateMyImg(MultipartFile image, String phone);
}