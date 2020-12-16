package se.ranking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="result_id")
    private Long id;
    private String discipline;
    private String reportedPerformance;
    private String announcedPerformance;
    private double points;
    private String card;
    private String remarks;
    private int day;

    @JsonBackReference(value = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference(value = "competition")
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "competition_id")
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

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
