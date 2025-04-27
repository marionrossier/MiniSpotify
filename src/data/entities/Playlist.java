package data.entities;

import services.Cookies_SingeltonPattern;
import services.TransverseService;

import java.util.*;

public class Playlist {
    private String playlistName;
    private int playlistId;
    private LinkedList<Integer> playlistSongsId = new LinkedList<>();
    private int playlistSeconds;
    private int playlistSize;
    private int ownerId;
    private TransverseService transverseService = new TransverseService();

    public Playlist (){}

    public Playlist(String playlistName) {
        this.playlistName = playlistName;
        this.playlistId = transverseService.setUniqueId();
        this.ownerId = Cookies_SingeltonPattern.getInstance().getUserId();
    }

    public Playlist(String playlistName, LinkedList <Integer> playlistSongsId, int playlistSeconds, int playlistSize) {
        this.playlistName = playlistName;
        this.playlistId = transverseService.setUniqueId();
        this.ownerId = 0;
        this.playlistSongsId = playlistSongsId;
        this.playlistSeconds = playlistSeconds;
        this.playlistSize = playlistSize;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public LinkedList<Integer> getPlaylistSongsListWithId() {
        return playlistSongsId;
    }

    public void setPlaylistSongsId(LinkedList<Integer> playlistSongsId) {
        this.playlistSongsId = playlistSongsId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getPlaylistSeconds() {
        return playlistSeconds;
    }

    public void setPlaylistDuration() {
        int totalSeconds = 0;
        for (int i = 0; i < playlistSongsId.size(); i++) {
            Song currentSong = new Song();
            currentSong.setSongId(playlistSongsId.get(i));
            totalSeconds += currentSong.getSeconds();
        }
        this.playlistSeconds = totalSeconds;
    }
    public void addSong(Song currentSong) {
        playlistSongsId.add(currentSong.getSongId());
        playlistSeconds += currentSong.getSeconds();
    }

    public int getPlaylistSize() {
        return playlistSize;
    }

    public void setPlaylistSize() {
        this.playlistSize = playlistSongsId.size();
    }

    public void removeSong(Song currentSong) {
        playlistSongsId.remove(currentSong.getSongId());
        playlistSeconds -= currentSong.getSeconds();
    }

    public void reorderSong(int songIndex) {
        int memory = playlistSongsId.get(songIndex);
        playlistSongsId.remove(songIndex);
        playlistSongsId.add(songIndex, memory);
    }

}
