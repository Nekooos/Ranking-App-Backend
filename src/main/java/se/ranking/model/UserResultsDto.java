package se.ranking.model;

public class UserResultsDto {
    private Long id;
    private String name;
    private Discipline discipline;
    private String reportedPerformance;
    private String announcedPerformance;
    private Double points;
    private Card card;
    private String remarks;
    private String date;


    public UserResultsDto(Long id, String name, Discipline discipline, String reportedPerformance, String announcedPerformance, Double points, Card card, String remarks, String date) {
        this.id = id;
        this.name = name;
        this.discipline = discipline;
        this.reportedPerformance = reportedPerformance;
        this.announcedPerformance = announcedPerformance;
        this.points = points;
        this.card = card;
        this.remarks = remarks;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
