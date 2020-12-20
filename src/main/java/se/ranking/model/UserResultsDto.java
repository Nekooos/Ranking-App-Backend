package se.ranking.model;

public class UserResultsDto {
    private Long id;
    private String name;
    private String discipline;
    private String reportedPerformance;
    private String announcedPerformance;
    private double points;
    private String card;
    private String remarks;

    public UserResultsDto(Long id, String name, String discipline, String reportedPerformance, String announcedPerformance, double points, String card, String remarks) {
        this.id = id;
        this.name = name;
        this.discipline = discipline;
        this.reportedPerformance = reportedPerformance;
        this.announcedPerformance = announcedPerformance;
        this.points = points;
        this.card = card;
        this.remarks = remarks;
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
}
