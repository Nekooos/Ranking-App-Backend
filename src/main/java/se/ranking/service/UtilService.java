package se.ranking.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Map;

public interface UtilService {
    double convertStaticApneaTimeToPoints(String time);
    double convertConstantWeightMetersToPoints(String meters);
    double convertDynamicApneaMetersToPoints(String meters);
    double convertStringToSeconds(String time);
    LocalDate stringToLocalDate(String date);
    Map<String, Object> getFieldErrorResponse(BindingResult bindingResult);
    ResponseEntity<Object> fieldErrorResponse(String message, Object fieldError);
}
