package data.entities;

import data.jsons.SongRepository;
import services.UniqueIdService;

import java.util.*;

public class Playlist {
    private String name;
    private int playlistId;
    private LinkedList<Integer> listSongsId = new LinkedList<>();
    private int durationSeconds;
    private int size;
    private int ownerId;
    private PlaylistEnum status;
    private final UniqueIdService uniqueIdService = new UniqueIdService();

    public Playlist (){}

    public Playlist(String name, PlaylistEnum status) {
        this.name = name;
        this.playlistId = uniqueIdService.setUniqueId();
        this.status = status;
    }

    public Playlist(String name, LinkedList <Integer> listSongsId, int durationSeconds, int size, int ownerId, PlaylistEnum status) {
        this.name = name;
        this.playlistId = uniqueIdService.setUniqueId();
        this.ownerId = 0;
        this.listSongsId = listSongsId;
        this.durationSeconds = durationSeconds;
        this.size = size;
        this.ownerId = ownerId;
        this.status = status;
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
        SongRepository songRepository = new SongRepository();
        int totalSeconds = 0;
        for (Integer integer : this.getPlaylistSongsListWithId()) {
            Song song = songRepository.getSongById(integer);
            totalSeconds += song.getDurationSeconds();
        }
        return totalSeconds;
    }

    public void setPlaylistDuration(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public int getSize() {
        return this.getPlaylistSongsListWithId().size();
    }

    public void setPlaylistSize(int playlistSize) {
        this.size = playlistSize;
    }

    public void setPlaylistInformation(int playlistDuration, int playlistSize){
        setPlaylistDuration(playlistDuration);
        setPlaylistSize(playlistSize);
    }

    public PlaylistEnum getStatus() {
        return status;
    }

    public void setStatus(PlaylistEnum status) {
        this.status = status;
    }
}
