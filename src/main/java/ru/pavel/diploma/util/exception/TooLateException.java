package ru.pavel.diploma.util.exception;

public class TooLateException extends RuntimeException {
    public TooLateException(String message) {
        super(message);
    }
}