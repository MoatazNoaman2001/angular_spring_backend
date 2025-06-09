package com.moataz.examPlatform.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super("user already exists");
    }
}
