package data.entities;

import services.UniqueIdService;

import java.util.LinkedList;

public class Artist {
    private String artistName;
    private int artistId;
    private LinkedList<Integer> artistSongsID = new LinkedList<>();
    private final UniqueIdService uniqueIdService = new UniqueIdService();

    public Artist() {
    }

    public Artist(String artistName) {
        this.artistName = artistName;
        this.artistId = uniqueIdService.setUniqueId();
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
    public void setArtistId(int id) {
        this.artistId = id;
    }
    public void setArtistSongsID(LinkedList<Integer> artistSongsID) {
        this.artistSongsID = artistSongsID;
    }
}

