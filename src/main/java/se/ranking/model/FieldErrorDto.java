package se.ranking.model;

import java.util.ArrayList;
import java.util.List;

public class FieldErrorDto {
    private final String objectName;
    private final String field;
    private final String message;
    private List<FieldErrorDto> fieldErrors;

    public FieldErrorDto(String objectName, String field, String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDto(objectName, field, message));
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorDto> getFieldErrors() {
        return fieldErrors;
    }
}
