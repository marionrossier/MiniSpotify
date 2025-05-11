package serverSide.entities;

import clientSide.services.UniqueIdService;

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

    //JsonPopulate
    public Artist(int artistId, String artistName, LinkedList<Integer> artistSongsId) {
        this.artistName = artistName;
        this.artistId = artistId;
        this.artistSongsID = artistSongsId;
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

