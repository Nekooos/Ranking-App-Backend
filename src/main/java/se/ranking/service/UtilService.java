package se.ranking.service;

import java.time.LocalDate;

public interface UtilService {
    double convertStaticApneaTimeToPoints(String time);
    double convertConstantWeightMetersToPoints(String meters);
    double convertDynamicApneaMetersToPoints(String meters);
    double convertStringToSeconds(String time);
    LocalDate stringToLocalDateTime(String date);
}
