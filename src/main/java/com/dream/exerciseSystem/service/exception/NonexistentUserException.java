package com.dream.exerciseSystem.service.exception;

public class NonexistentUserException extends Exception {
    public NonexistentUserException(String msg) {
        super(msg);
    }
}
