package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;

public interface TeacherService {

    TeacherDashboardDto getTeacherDashboard(User user);

    UpdateProfileResponse updateProfile(User me, UpdateTeacherProfileRequest request);

    UserDto addStudent(AddStudentRequest userDto);
}
