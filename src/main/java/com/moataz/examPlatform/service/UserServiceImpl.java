package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.Subject;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.repository.ExamRepository;
import com.moataz.examPlatform.repository.ExamsAttemptRepository;
import com.moataz.examPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final FileServices fileServices;
    private final ExamRepository examRepository;
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
    public UserDto getMyProfile(User me) {
        List<Exam> createdExams = examRepository.findByCreatedByUserId(me.getUserId());
        Set<Subject> subjects = createdExams.stream().map(Exam::getSubject).collect(Collectors.toSet());
        UserDto userDto = convertToDto(me);
        userDto.setIsVerified(me.getIsVerified());
        userDto.setCreatedExams(createdExams);
        userDto.setMySubjects(subjects);
        return userDto;
    }

    @Override
    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getUserType())
                .image(user.getImage())
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
    public UpdateProfileResponse updateProfile(User me, UpdateProfileRequest request) {
        if (request.getName() != null) {
            me.setName(request.getName());
        }
        if (request.getPhoneNumber() != null) {
            me.setPhone(request.getPhoneNumber());
        }
        if (request.getEmail() != null) {
            me.setEmail(request.getEmail());
        }
        if (request.getAddress() != null) {
            me.setAddress(request.getAddress());
        }
        repository.save(me);
        String jwt = new JwtService().generateToken(me);
        UpdateProfileResponse response = UpdateProfileResponse.builder().token(jwt).user(convertToDto(me)).build();
        return response;
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
