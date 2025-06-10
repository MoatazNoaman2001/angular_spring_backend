package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.ExamAttempts;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.repository.ExamsAttemptRepository;
import com.moataz.examPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final FileServices fileServices;
    private final ExamsAttemptRepository examsAttemptRepository;

    @Value("${project.location}")
    String path;

    @Value("${base.url}")
    String url;

    @Override
    public User getUserProfile(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

    @Override
    public RegisterRequest convertToDto(User user) {
        return RegisterRequest.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getUserType())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    public User loadUserByUsername(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("no user with this email"));
    }

    @Override
    public String updateMyImg(MultipartFile image, String email) {
        boolean isUserExist = repository.findByEmail(email).isPresent();
        if (isUserExist) {
            User user = repository.findByEmail(email).get();
            String fileName = "";
            try {
                fileName = fileServices.uploadFile(path, image);
                user.setImage(fileName);
                repository.saveAndFlush(user);
                return "updated";
            } catch (IOException e) {
                throw new RuntimeException("Cant save image");
            }
        }
        throw new UsernameNotFoundException("cant find user with this email");
    }
}
