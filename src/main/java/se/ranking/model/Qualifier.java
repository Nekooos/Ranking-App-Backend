package se.ranking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Qualifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "Value to qualify is required")
    private String valueToQualify;
    @NotNull(message = "Start date is required")
    private String startDate;
    @NotNull(message = "End date is required")
    private String endDate;
    @NotNull(message = "Discipline is required")
    private Discipline discipline;

    @JsonManagedReference(value = "qualifierAnswer")
    @OneToMany(mappedBy = "qualifier")
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
