package se.ranking.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Qualifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String timeToQualify;
    @ManyToOne(targetEntity = User.class)
    private List<User> users;

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

    public String getTimeToQualify() {
        return timeToQualify;
    }

    public void setTimeToQualify(String timeToQualify) {
        this.timeToQualify = timeToQualify;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
