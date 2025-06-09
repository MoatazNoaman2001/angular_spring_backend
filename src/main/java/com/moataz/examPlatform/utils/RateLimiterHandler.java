//package com.moataz.examPlatform.utils;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.Duration;
//import java.util.concurrent.ConcurrentHashMap;
//
//@ControllerAdvice
//public class RateLimitHandler {
//    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
//
//    @Before("@annotation(RateLimited)")
//    public void rateLimit(HttpServletRequest request) {
//        String ip = request.getRemoteAddr();
//        Bucket bucket = buckets.computeIfAbsent(ip, k ->
//                Bucket.builder()
//                        .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)))
//                                .build()
//                        );
//
//        if (!bucket.tryConsume(1)) {
//            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
//        }
//    }
//}