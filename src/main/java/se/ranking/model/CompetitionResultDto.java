package se.ranking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public interface CompetitionResultDto {
    String getFirst_name();
    String getLast_name();
    Long getId();
    String getDiscipline();
    String getReported_performance();
    String getAnnounced_performance();
    int getPoints();
    String getCard();
    String getRemarks();
    int getDay();
    Long getResult_id();
}
