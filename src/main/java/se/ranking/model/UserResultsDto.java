package se.ranking.model;

public class UserResultsDto {
    private Long id;
    private String name;
    private String discipline;

    public UserResultsDto(Long id, String name, String discipline) {
        this.id = id;
        this.name = name;
        this.discipline = discipline;
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
}
