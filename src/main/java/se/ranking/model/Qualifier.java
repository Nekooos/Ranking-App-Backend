package se.ranking.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Qualifier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double valueToQualify;
    @ManyToMany(targetEntity = User.class)
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public double getValueToQualify() {
        return valueToQualify;
    }

    public void setValueToQualify(double valueToQualify) {
        this.valueToQualify = valueToQualify;
    }
}
