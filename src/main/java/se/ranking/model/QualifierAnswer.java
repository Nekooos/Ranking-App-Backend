package se.ranking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QualifierAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean answer;
    private boolean qualified;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    RegisteredUser registeredUser;

    @ManyToOne
    @JoinColumn(name = "qualifier_id")
    Qualifier qualifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public RegisteredUser getUser() {
        return registeredUser;
    }

    public void setUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public Qualifier getQualifier() {
        return qualifier;
    }

    public void setQualifier(Qualifier qualifier) {
        this.qualifier = qualifier;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isQualified() {
        return qualified;
    }

    public void setQualified(boolean qualified) {
        this.qualified = qualified;
    }
}
