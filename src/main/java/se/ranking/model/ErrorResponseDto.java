package se.ranking.model;

import java.util.List;

public class ErrorResponseDto {
    private String message;
    private List<FieldErrorDto> fieldErrors;

    public ErrorResponseDto(String message) {
        this.message = message;
    }

    public ErrorResponseDto(List<FieldErrorDto> fieldErrors, String message) {
        this.fieldErrors = fieldErrors;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorDto> getFieldErrors() {
        return fieldErrors;
    }
}
