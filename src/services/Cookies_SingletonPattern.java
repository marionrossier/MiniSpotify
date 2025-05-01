package services;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;

import java.util.LinkedList;
import java.util.List;

public class Cookies_SingletonPattern {
    private static Cookies_SingletonPattern instance;

    private final int userId;
    private int currentPlaylistId;
    private int currentSongId;
    private final PlaylistRepository playlistRepository = new PlaylistRepository();
    private List<Integer> temporaryPlaylist;

    private Cookies_SingletonPattern(int userId) {
        this.userId = userId;
        this.currentPlaylistId = playlistRepository.getPlaylistByName("AllSongs").getPlaylistId();
        this.currentSongId = playlistRepository.getPlaylistByName("AllSongs").getPlaylistSongsListWithId().getFirst();
        this.temporaryPlaylist = null;
    }

    public static Cookies_SingletonPattern setUser(int userId) {
        if (instance == null || instance.userId == 1) {
            instance = new Cookies_SingletonPattern(userId);
        }
        return instance;
    }

    public static void setCurrentPlaylistId(int currentPlaylistId) {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized. Please set the user first.");
        }
        instance.currentPlaylistId = currentPlaylistId;
    }

    public static void setCurrentSongId(int currentSongId) {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized. Please set the user first.");
        }
        instance.currentSongId = currentSongId;
    }

    public static void setTemporaryPlaylist(LinkedList<Integer> temporaryPlaylist) {
        if (instance == null) {
            throw new IllegalStateException("Cookies instance not initialized. Please set the user first.");
        }
        instance.temporaryPlaylist = temporaryPlaylist;

        Playlist temporaryPlaylistObj = new Playlist("temporaryPlaylist");
        temporaryPlaylistObj.setPlaylistSongsId(temporaryPlaylist);

        instance.currentPlaylistId = instance.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId();
        instance.currentSongId = instance.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistSongsListWithId().getFirst();
    }

    public static void setInstance(int id) {
        if (instance == null) {
            instance = new Cookies_SingletonPattern(id);
        }
    }

    public static void resetCookies() {
        if (instance != null){
            if (Cookies_SingletonPattern.getInstance().getTemporaryPlaylist() != null) {
                instance.playlistRepository.deletePlaylistById(instance.playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId());
            }
            instance = null;
        }

    }

    public static Cookies_SingletonPattern getInstance() {
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