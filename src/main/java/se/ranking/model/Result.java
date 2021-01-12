package se.ranking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="result_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "discipline is required")
    private Discipline discipline;
    @NotNull(message = "Reported Performance is required")
    private String reportedPerformance;
    @NotNull(message = "Announced performance is required")
    private String announcedPerformance;
    @NotNull(message = "Points is required")
    private Double points;
    @NotNull(message = "Card is required")
    @Enumerated(EnumType.STRING)
    private Card card;

    private String remarks;
    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message ="Date must match yyyy-MM-dd")
    private String date;

    @Column(insertable = false, updatable = false)
    private Long userId;

    @Column(insertable = false, updatable = false)
    private Long notRegisteredUserId;

    @Column(insertable = false, updatable = false)
    private Long competitionId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private RegisteredUser registeredUser;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notRegisteredUserId")
    private NotRegisteredUser notRegisteredUser;

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

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public RegisteredUser getUser() {
        return registeredUser;
    }

    public void setUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public NotRegisteredUser getNotRegisteredUser() {
        return notRegisteredUser;
    }

    public void setNotRegisteredUser(NotRegisteredUser notRegisteredUser) {
        this.notRegisteredUser = notRegisteredUser;
    }
}
