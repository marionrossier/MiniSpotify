package entities;

import java.util.*;

public class Playlist {
    private String playlistName;
    private String playlistGuId;
    private LinkedList<Song> playlistSongs;
    private double playlistDuration;

    public Playlist(String playlistName, String playlistGuId) {
        this.playlistName = playlistName;
        this.playlistGuId = playlistGuId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistGuId() {
        return playlistGuId;
    }

    public void setPlaylistGuId(String playlistGuId) {
        this.playlistGuId = playlistGuId;
    }

    public LinkedList<Song> getPlaylistSongs() {
        return playlistSongs;
    }

    public void setPlaylistSongs(LinkedList<Song> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

    public double getPlaylistDuration() {
        return playlistDuration;
    }

    public void setPlaylistDuration(double playlistDuration) {
        this.playlistDuration = playlistDuration;
    }

    public void addSong(Song currentSong) {/*TODO*/}
    public void removeSong() {/*TODO*/}
    public void reorderSongs() {/*TODO*/}
}
