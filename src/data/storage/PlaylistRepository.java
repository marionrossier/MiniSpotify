package data.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.Playlist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepository {
    private final String filePath;
    private final ObjectMapper objectMapper;

    public PlaylistRepository(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    public PlaylistRepository() {
        this.filePath = "src/data/storage/playlist.json";
        this.objectMapper = new ObjectMapper();
    }

    public List<Playlist> getAllPlaylists() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Playlist>>() {});
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des playlists : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAllPlaylists(List<Playlist> playlists) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), playlists);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des playlists : " + e.getMessage());
        }
    }

    public void addPlaylist(Playlist playlist) {
        List<Playlist> playlists = getAllPlaylists();
        playlists.add(playlist);
        saveAllPlaylists(playlists);
    }

    public void removePlaylistById(int playlistId) {
        List<Playlist> playlists = getAllPlaylists();
        playlists.removeIf(playlist -> playlist.getPlaylistId() == playlistId);
        saveAllPlaylists(playlists);
    }

    public Playlist findPlaylistById(int playlistId) {
        return getAllPlaylists().stream()
                .filter(playlist -> playlist.getPlaylistId() == playlistId)
                .findFirst()
                .orElse(null);
    }

    public void updatePlaylist(Playlist updatedPlaylist) {
        List<Playlist> playlists = getAllPlaylists();
        for (int i = 0; i < playlists.size(); i++) {
            if (playlists.get(i).getPlaylistId() == updatedPlaylist.getPlaylistId()) {
                playlists.set(i, updatedPlaylist);
                saveAllPlaylists(playlists);
                return;
            }
        }
        System.err.println("Playlist avec l'ID " + updatedPlaylist.getPlaylistId() + " non trouvée.");
    }

    public Playlist findPlaylistByName(String name) {
        List<Playlist> playlists = getAllPlaylists();
        for (Playlist playlist : playlists) {
            if (playlist.getPlaylistName().equalsIgnoreCase(name)) {
                return playlist;
            }
        }
        return null;
    }
}
