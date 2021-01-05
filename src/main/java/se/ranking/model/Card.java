package se.ranking.model;

public enum Card {
    WHITE("white"),
    YELLOW("yellow"),
    RED("red");

    private final String value;

    Card(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
