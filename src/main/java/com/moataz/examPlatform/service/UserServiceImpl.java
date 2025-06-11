package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.repository.ExamsAttemptRepository;
import com.moataz.examPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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
    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getUserType())
                .location(user.getAddress())
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

    @Override
    public String deleteUser(String userId) {
        var user = repository.findById(UUID.fromString(userId));
        if (user.isEmpty()){
            user = repository.findByEmail(userId);
            if (user.isEmpty()){
                throw new UsernameNotFoundException("no user found");
            }
        }
        repository.delete(user.get());
        return "deleted";
    }

    @Override
    public User updateProfile(User me, UpdateProfileRequest request) {
        me.setName(request.getName());
        me.setPhone(request.getPhoneNumber());
        me.setEmail(request.getEmail());
        repository.save(me);
        return me;
    }

    @Override
    public User updateProfileImage(User me, MultipartFile image) {
        try {
            String fileName = fileServices.uploadFile(path, image);
            me.setImage(fileName);
            repository.save(me);
            return me;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }
}
