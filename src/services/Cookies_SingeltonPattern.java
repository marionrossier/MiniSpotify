package services;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Cookies_SingeltonPattern {
    private static Cookies_SingeltonPattern instance;

    private final int userId;
    private int currentPlaylistId;
    private int currentSongId;
    private PlaylistRepository playlistRepository = new PlaylistRepository();
    private List<Integer> temporaryPlaylist = new ArrayList<>();

    private Cookies_SingeltonPattern(int userId) {
        this.userId = userId;
        this.currentPlaylistId = playlistRepository.getPlaylistByName("AllSongs").getPlaylistId();
        this.currentSongId = playlistRepository.getPlaylistByName("AllSongs").getPlaylistSongsListWithId().getFirst();
        this.temporaryPlaylist = null;
    }

    public static Cookies_SingeltonPattern setUser(int userId) {
        if (instance == null || instance.userId == 1) {
            instance = new Cookies_SingeltonPattern(userId);
        }
        return instance;
    }

    public static Cookies_SingeltonPattern setCurrentPlaylistId(int currentPlaylistId) {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized. Please set the user first.");
        }
        instance.currentPlaylistId = currentPlaylistId;
        return instance;
    }

    public static Cookies_SingeltonPattern setCurrentSongId(int currentSongId) {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized. Please set the user first.");
        }
        instance.currentSongId = currentSongId;
        return instance;
    }

    public static Cookies_SingeltonPattern setTemporaryPlaylist(List<Integer> temporaryPlaylist) {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized. Please set the user first.");
        }
        instance.temporaryPlaylist = temporaryPlaylist;

        Playlist temporaryPlaylistObj = new Playlist("temporaryPlaylist");
        temporaryPlaylistObj.setPlaylistSongsId((LinkedList<Integer>) temporaryPlaylist);


        instance.currentPlaylistId = instance.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId();
        instance.currentSongId = instance.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistSongsListWithId().getFirst();
        return instance;
    }

    public static Cookies_SingeltonPattern setInstance(int id) {
        if (instance == null) {
            instance = new Cookies_SingeltonPattern(id);
        }
        return instance;
    }

    public static Cookies_SingeltonPattern resetCookies() {
        if (instance != null){
            if (Cookies_SingeltonPattern.getInstance().getTemporaryPlaylist() != null) {
                instance.playlistRepository.deletePlaylistById(instance.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId());
            }
            instance = null;
        }

        return instance;
    }

    public static Cookies_SingeltonPattern getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized.");
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public int getCurrentPlaylistId() {
        return currentPlaylistId;
    }

    public int getCurrentSongId() {
        return currentSongId;
    }

    public List<Integer> getTemporaryPlaylist() {
        return temporaryPlaylist;
    }

    @Override
    public String toString() {
        return "Cookies{" +
                "userId=" + userId +
                ", currentPlaylistId=" + currentPlaylistId +
                '}';
    }
}