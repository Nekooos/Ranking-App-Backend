package se.ranking.model;

public interface CompetitionResultDto {
    String getFirst_name();
    String getLast_name();
    Long getId();
    String getDiscipline();
    String getReported_performance();
    String getAnnounced_performance();
    double getPoints();
    String getCard();
    String getRemarks();
    int getDay();
    Long getResult_id();
}
