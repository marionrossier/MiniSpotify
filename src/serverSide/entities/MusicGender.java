package serverSide.entities;

public enum MusicGender {
    SOUL_RNB("Soul RnB"),
    POP("Pop"),
    HIP_HOP("Hip Hop"),
    ROCK("Rock"),
    FRENCH_VARIETY("French Variety"),
    ELECTRO("Electro"),
    DISCO("Disco"),
    REGGAE("Reggae"),
    NULL("Null"),;

    private final String displayName;

    MusicGender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}