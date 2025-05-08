package data.entities;

import services.UniqueIdService;

import java.util.*;

public class Playlist {
    private String name;
    private int playlistId;
    private LinkedList<Integer> listSongsId = new LinkedList<>();
    private int durationSeconds;
    private int size;
    private int ownerId;
    private final UniqueIdService uniqueIdService = new UniqueIdService();

    public Playlist (){}

    public Playlist(String name) {
        this.name = name;
        this.playlistId = uniqueIdService.setUniqueId();
    }

    public Playlist(String name, LinkedList <Integer> listSongsId, int durationSeconds, int size) {
        this.name = name;
        this.playlistId = uniqueIdService.setUniqueId();
        this.ownerId = 0;
        this.listSongsId = listSongsId;
        this.durationSeconds = durationSeconds;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public LinkedList<Integer> getPlaylistSongsListWithId() {
        return listSongsId;
    }

    public void setListSongsId(LinkedList<Integer> listSongsId) {
        this.listSongsId = listSongsId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setPlaylistDuration() {
        int totalSeconds = 0;
        for (Integer integer : listSongsId) {
            Song currentSong = new Song();
            currentSong.setSongId(integer);
            totalSeconds += currentSong.getDurationSeconds();
        }
        this.durationSeconds = totalSeconds;
    }

    public int getSize() {
        return size;
    }

    public void setPlaylistSize() {
        this.size = listSongsId.size();
    }

    //TODO : ajuster car ne met pas a jour les informations contenues !
    public void setPlaylistInformation(){
        setPlaylistDuration();
        setPlaylistSize();
    }
}
