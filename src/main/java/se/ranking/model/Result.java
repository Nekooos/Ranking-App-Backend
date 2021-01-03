package se.ranking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="result_id")
    private Long id;
    @NotNull
    private String discipline;
    @NotNull
    private String reportedPerformance;
    @NotNull
    private String announcedPerformance;
    @NotNull
    private Double points;
    @NotNull
    private String card;

    private String remarks;

    private LocalDateTime date;

    @Column(insertable = false, updatable = false)
    private Long userId;

    @Column(insertable = false, updatable = false)
    private Long competitionId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonBackReference(value = "competition")
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "competitionId")
    private Competition competition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getReportedPerformance() {
        return reportedPerformance;
    }

    public void setReportedPerformance(String reportedPerformance) {
        this.reportedPerformance = reportedPerformance;
    }

    public String getAnnouncedPerformance() {
        return announcedPerformance;
    }

    public void setAnnouncedPerformance(String announcedPerformance) {
        this.announcedPerformance = announcedPerformance;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
