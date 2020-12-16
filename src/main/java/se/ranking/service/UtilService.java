package se.ranking.service;

public interface UtilService {
    double convertStaticApneaTimeToPoints(String time);
    double convertConstantWeightMetersToPoints(String meters);
    double convertDynamicApneaMetersToPoints(String meters);
}
