package se.ranking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Qualifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String valueToQualify;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private Discipline discipline;

    @OneToMany(mappedBy = "user")
    Set<QualifierAnswer> qualifierUsers;

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

    public String getValueToQualify() {
        return valueToQualify;
    }

    public void setValueToQualify(String valueToQualify) {
        this.valueToQualify = valueToQualify;
    }

    public Set<QualifierAnswer> getQualifierUsers() {
        return qualifierUsers;
    }

    public void setQualifierUsers(Set<QualifierAnswer> qualifierUsers) {
        this.qualifierUsers = qualifierUsers;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
