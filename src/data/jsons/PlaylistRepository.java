package data.jsons;

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
        this.filePath = "src/data/jsons/playlist.json";
        this.objectMapper = new ObjectMapper();
    }

    public List<Playlist> getAllPlaylists() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            List<Playlist> playlists = objectMapper.readValue(file, new TypeReference<>() {});
            if (playlists == null || playlists.isEmpty()) {
                return new ArrayList<>();
            }
            return playlists;
        } catch (IOException e) {
            System.err.println("Error during the playlists upload : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void savePlaylist(Playlist playlist) {
        List<Playlist> playlists = getAllPlaylists();
        playlists.removeIf(p -> p.getPlaylistId() == playlist.getPlaylistId());
        playlists.add(playlist);
        saveAllPlaylists(playlists);
    }

    private void saveAllPlaylists(List<Playlist> playlists) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), playlists);
        } catch (IOException e) {
            System.err.println("Error during the saving process for the playlists : " + e.getMessage());
        }
    }

    public void deletePlaylistById(int playlistId) {
        List<Playlist> playlists = getAllPlaylists();
        playlists.removeIf(playlist -> playlist.getPlaylistId() == playlistId);
        saveAllPlaylists(playlists);
    }

    public Playlist getPlaylistById(int playlistId) {
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
        System.err.println("Playlist with ID " + updatedPlaylist.getPlaylistId() + " not found.");
    }

    public Playlist getPlaylistByName(String name) {
        List<Playlist> playlists = getAllPlaylists();
        for (Playlist playlist : playlists) {
            if (playlist.getPlaylistName().equalsIgnoreCase(name)) {
                return playlist;
            }
        }
        return null;
    }
}
