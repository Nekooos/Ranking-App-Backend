package se.ranking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class RegisteredUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Email is required")
    @Email(message = "Invalid Email")
    @Column(nullable = false)
    private String email;
    @JsonIgnore
    private String password;
    @Column(nullable = false, name ="first_name")
    @NotNull(message = "First name is required")
    private String firstName;
    @Column(nullable = false, name = "last_name")
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotNull(message = "gender is required")
    private String gender;
    private String club;
    private boolean registered;

    //change to set
    //@ManyToMany(mappedBy = "users")
    //List<Competition> competitions;

    @JsonManagedReference(value = "result")
    @OneToMany(fetch= FetchType.LAZY, mappedBy = "registeredUser")
    List<Result> results;

    @JsonManagedReference(value = "userAnswer")
    @OneToMany(fetch= FetchType.LAZY, mappedBy = "qualifier")
    Set<QualifierAnswer> qualifierAnswers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /*public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }*/

    public Set<QualifierAnswer> getQualifierUsers() {
        return qualifierAnswers;
    }

    public void setQualifierUsers(Set<QualifierAnswer> qualifierUsers) {
        this.qualifierAnswers = qualifierUsers;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public Set<QualifierAnswer> getQualifierAnswers() {
        return qualifierAnswers;
    }

    public void setQualifierAnswers(Set<QualifierAnswer> qualifierAnswers) {
        this.qualifierAnswers = qualifierAnswers;
    }
}
