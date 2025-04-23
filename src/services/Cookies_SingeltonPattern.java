package services;

import data.jsons.PlaylistRepository;

public class Cookies_SingeltonPattern {
    private static Cookies_SingeltonPattern instance;

    private final int userId;
    private int currentPlaylistId;
    private int currentSongId;
    private PlaylistRepository playlistRepository = new PlaylistRepository();

    private Cookies_SingeltonPattern(int userId) {
        this.userId = userId;
        this.currentPlaylistId = playlistRepository.getPlaylistByName("AllSongs").getPlaylistId();
        this.currentSongId = playlistRepository.getPlaylistByName("AllSongs").getPlaylistSongsId().getFirst();
    }

    public static Cookies_SingeltonPattern setUser(int userId) {
        if (instance == null) {
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

    public static Cookies_SingeltonPattern setInstance(int id) {
        if (instance == null) {
            instance = new Cookies_SingeltonPattern(id);
        }
        return instance;
    }

    public static Cookies_SingeltonPattern resetCookies() {
        instance = null;
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

    @Override
    public String toString() {
        return "Cookies{" +
                "userId=" + userId +
                ", currentPlaylistId=" + currentPlaylistId +
                '}';
    }
}