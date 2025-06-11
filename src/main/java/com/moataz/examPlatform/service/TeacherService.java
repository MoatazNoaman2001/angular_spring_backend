package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.TeacherDashboardDto;
import com.moataz.examPlatform.model.User;

public interface TeacherService {

    TeacherDashboardDto getTeacherDashboard(User user);
}
