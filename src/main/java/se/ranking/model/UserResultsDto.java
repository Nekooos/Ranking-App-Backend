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
    private String date;
    private String endDate;

    public UserResultsDto(Long id, String name, String discipline, String reportedPerformance, String announcedPerformance, double points, String card, String remarks, String date, String endDate) {
        this.id = id;
        this.name = name;
        this.discipline = discipline;
        this.reportedPerformance = reportedPerformance;
        this.announcedPerformance = announcedPerformance;
        this.points = points;
        this.card = card;
        this.remarks = remarks;
        this.date = date;
        this.endDate = endDate;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
