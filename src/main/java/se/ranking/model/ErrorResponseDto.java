package se.ranking.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ErrorResponseDto {
    private String message;
    private Throwable cause;
    private String causeMessage;
    private String stackTrace;
    private String dateTime;
    private List<FieldErrorDto> fieldErrors;

    public ErrorResponseDto(String message) {
        this.message = message;
    }

    public ErrorResponseDto(List<FieldErrorDto> fieldErrors, String message) {
        this.fieldErrors = fieldErrors;
        this.message = message;
    }

    public ErrorResponseDto(String message, Throwable cause, String causeMessage, String stackTrace, String dateTime) {
        this.message = message;
        this.cause = cause;
        this.causeMessage = causeMessage;
        this.stackTrace = stackTrace;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getCauseMessage() {
        return causeMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public String getDateTime() {
        return dateTime;
    }
}
