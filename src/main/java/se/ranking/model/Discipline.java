package se.ranking.model;

public enum Discipline {
    STA("STA"),
    FEN("FEN");
    //More

    private final String value;

    Discipline(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
