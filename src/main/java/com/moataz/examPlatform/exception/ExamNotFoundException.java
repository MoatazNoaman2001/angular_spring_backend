package com.moataz.examPlatform.exception;

public class ExamNotFoundException extends RuntimeException {

    public ExamNotFoundException() {
        super("Exam Not Found With Such Id");
    }
}
