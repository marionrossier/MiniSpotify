package data.entities;

import data.storage.PlaylistRepository;

import java.util.*;

public class Playlist {
    private String playlistName;
    private int playlistId;
    private LinkedList<Integer> playlistSongs = new LinkedList<>();
    private double playlistDuration;

    public Playlist (){}

    public Playlist(String playlistName, int playlistId) {
        this.playlistName = playlistName;
        this.playlistId = playlistId;
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

    public LinkedList<Integer> getPlaylistSongs() {
        PlaylistRepository playlistRepository = new PlaylistRepository();
        playlistRepository.findPlaylistById(playlistId);
        return playlistSongs;
    }

    public void setPlaylistSongs(LinkedList<Integer> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

    public double getPlaylistDuration() {
        return playlistDuration;
    }

    public void setPlaylistDuration(double playlistDuration) {
        this.playlistDuration = playlistDuration;
    }
    public void addSong(Song currentSong) {
        playlistSongs.add(currentSong.getSongId());
        playlistDuration += currentSong.getDuration();
    }

    public void removeSong(Song currentSong) {
        playlistSongs.remove(currentSong.getSongId());
        playlistDuration -= currentSong.getDuration();
    }

    public void reorderSong(int songIndex) {
        int memory = playlistSongs.get(songIndex);
        playlistSongs.remove(songIndex);
        playlistSongs.add(songIndex, memory);
    }

}
