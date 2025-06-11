package com.moataz.examPlatform.service;

import com.moataz.examPlatform.dto.*;
import com.moataz.examPlatform.exception.UserAlreadyExistsException;
import com.moataz.examPlatform.model.User;
import com.moataz.examPlatform.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService , UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    public AuthResponse register(UserDto userDto, HttpSession session) {
        if (repository.findByEmail(userDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        var user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .userType(userDto.getRole())
                .isFirstTime(false)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        session.setAttribute("jwt" , jwtToken);
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .isFirstTime(false)
                .role(user.getUserType().name())
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request, HttpSession session) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        session.setAttribute("jwt" , jwtToken);
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getUserType().name())
                .isFirstTime(user.getIsFirstTime())
                .build();
    }

    @Override
    public String verify(User user) {
        String newToken = jwtService.generateToken(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getName());
        variables.put("activationLink", "http://localhost:8080/activate?token=" + newToken);

        emailService.sendEmailWithTemplate(
                user.getEmail(),
                "Welcome to Our Platform",
                "welcome-email",
                variables
        );
        return "Verify Email sent to your mail box";
    }

    @Override
    public String forgetPassword(ForgetRequest request) {
        User user= loadUserByUsername(request.getEmail());
        String newToken = jwtService.generateToken(loadUserByUsername(request.getEmail()));
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getName());
        variables.put("activationLink", "https://yourapp.com/activate?token=" + newToken);

        emailService.sendEmailWithTemplate(
                user.getEmail(),
                "Welcome to Our Platform",
                "welcome-email",
                variables
        );
        return "Reset Email sent to your mail box";
    }

    @Override
    public String isRightOwner(String token) {
//        if (isTokenUsed(token)) {
//            return "Link already used!";
//        }
        String email = jwtService.extractUsername(token);
        User user = repository.findByEmail(email).orElseThrow();
        if (user.isEnabled()){
            if (user.getIsFirstTime()){
                return "verified, now change password";
            }else{
                if (user.getIsVerified() == null || !user.getIsVerified()) {
                    user.setIsVerified(true);
                    repository.save(user);
//                    markTokenAsUsed(token);
                    return "Verified! Thanks. Go back to your site.";
                }
                return "Account already verified!";
            }
        }
        return null;
    }

//    private boolean isTokenUsed(String token) {
//        Cache cache = cacheManager.getCache("usedTokens");
//        if (cache == null) {
//            return false;
//        }
//
//        Cache.ValueWrapper wrapper = cache.get(token);
//        return wrapper != null;
//    }
//
//    private void markTokenAsUsed(String token) {
//        cacheManager.getCache("usedTokens").put(token, true);
//    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        String email = jwtService.extractUsername(request.getToken());
        User user = repository.findByEmail(email).orElseThrow();
        if (user.isEnabled()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setIsFirstTime(false);
            repository.save(user);
            return "password Changed go to login";
        }
        throw new UsernameNotFoundException("email not found or user is disabled, contact admin");
    }


    @Override
    public User loadUserByUsername(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("no user with this email"));
    }
}
