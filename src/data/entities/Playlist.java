package data.entities;

import data.jsons.PlaylistRepository;
import services.TransverseService;

import java.util.*;

public class Playlist {
    private String playlistName;
    private int playlistId;
    private LinkedList<Integer> playlistSongs = new LinkedList<>();
    private double playlistDuration;
    private int playlistSize;
    private int ownerId; //TODO: implémenter
    private TransverseService transverseService = new TransverseService();

    public Playlist (){}

    public Playlist(String playlistName) {
        this.playlistName = playlistName;
        this.playlistId = transverseService.setUniqueId();
    }
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

    //TODO : a vérifier la méthode...
    public void setPlaylistDuration() {
        double totalDuration = 0;
        for (int i = 0; i < playlistSongs.size(); i++) {
            Song currentSong = new Song();
            currentSong.setSongId(playlistSongs.get(i));
            totalDuration += currentSong.getDuration();
        }
        this.playlistDuration = totalDuration;
    }
    public void addSong(Song currentSong) {
        playlistSongs.add(currentSong.getSongId());
        playlistDuration += currentSong.getDuration();
    }

    public int getPlaylistSize() {
        return playlistSize;
    }

    public void setPlaylistSize() {
        this.playlistSize = playlistSongs.size();
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
