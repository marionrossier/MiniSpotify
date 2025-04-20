package data.entities;

import services.TransverseCode;

import java.util.LinkedList;

public class Artist {
    private String artistName;
    private int artistId;
    private LinkedList<Integer> artistSongsID = new LinkedList<>();
    private final TransverseCode transverseCode = new TransverseCode();

    public Artist() {
    }

    public Artist(String artistName) {
        this.artistName = artistName;
        this.artistId = transverseCode.setUniqueId();
    }

    public String getArtistName() {
        return artistName;
    }
    public int getArtistId() {
        return artistId;
    }
    public LinkedList<Integer> getArtistSongsID() {
        return artistSongsID;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public void setArtistId() {
        this.artistId = transverseCode.setUniqueId();
    }
    public void setArtistSongsID(LinkedList<Integer> artistSongsID) {
        this.artistSongsID = artistSongsID;
    }
}

