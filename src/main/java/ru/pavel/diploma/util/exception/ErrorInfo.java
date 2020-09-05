package ru.pavel.diploma.util.exception;

public class ErrorInfo {
    private final String url;
    private final ru.pavel.diploma.util.exception.ErrorType type;
    private final String typeMessage;
    private final String[] details;

    public ErrorInfo(CharSequence url, ru.pavel.diploma.util.exception.ErrorType type, String typeMessage, String... details) {
        this.url = url.toString();
        this.type = type;
        this.typeMessage = typeMessage;
        this.details = details;
    }
}