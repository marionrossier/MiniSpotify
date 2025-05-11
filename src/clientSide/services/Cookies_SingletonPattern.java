package clientSide.services;

import serverSide.entities.Playlist;
import serverSide.repositories.PlaylistLocalRepository;
import serverSide.repositories.SongLocalRepository;

public class Cookies_SingletonPattern {
    private static Cookies_SingletonPattern instance;

    private final int userId;
    private int currentPlaylistId;
    private int currentSongId;
    private final PlaylistLocalRepository playlistLocalRepository = new PlaylistLocalRepository();
    private final SongLocalRepository songLocalRepository = new SongLocalRepository();
    private final PlaylistServices playlistServices = new PlaylistServices(playlistLocalRepository, songLocalRepository);

    private Cookies_SingletonPattern(int userId) {
        this.userId = userId;
        this.currentPlaylistId = playlistServices.getAllSongsPlaylistId();
        this.currentSongId = playlistLocalRepository.getPlaylistByName("AllSongs").getPlaylistSongsListWithId().getFirst();
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

    public static void setInstance(int id) {
        if (instance == null) {
            instance = new Cookies_SingletonPattern(id);
        }
    }

    public static void resetCookies() {
        if (instance != null) {
            Playlist playlist = instance.playlistLocalRepository.getPlaylistById(instance.playlistServices.getTemporaryPlaylistId());
            if (playlist != null) {
                instance.playlistServices.deleteTemporaryPlaylist();
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

    @Override
    public String toString() {
        return "Cookies{" +
                "userId=" + userId +
                ", currentPlaylistId=" + currentPlaylistId +
                '}';
    }
}