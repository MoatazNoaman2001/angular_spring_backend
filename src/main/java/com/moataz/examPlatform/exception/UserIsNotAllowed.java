package com.moataz.examPlatform.exception;

public class UserIsNotAllowed extends RuntimeException {
    public UserIsNotAllowed() {
        super("User is disabled");
    }
}
