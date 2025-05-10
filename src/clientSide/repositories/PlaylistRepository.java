package clientSide.repositories;

import clientSide.entities.Playlist;
import clientSide.entities.PlaylistEnum;
import clientSide.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class PlaylistRepository {
    private final String filePath;
    private final Jsons jsons;
    private List<Playlist> data;

    public PlaylistRepository(String filePath) {
        this.filePath = filePath;
        this.jsons = new Jsons();
        this.data = jsons.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public PlaylistRepository() {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/playlist.json");
    }

    public List<Playlist> getAllPlaylists() {
        return new ArrayList<>(data); // copie défensive
    }

    public void savePlaylist(Playlist playlist) {
        data.removeIf(p -> p.getPlaylistId() == playlist.getPlaylistId());
        data.add(playlist);
        jsons.saveToJson(filePath, data);
    }

    public void deletePlaylistById(int playlistId) {
        data.removeIf(playlist -> playlist.getPlaylistId() == playlistId);
        jsons.saveToJson(filePath, data);
    }

    public Playlist getPlaylistById(int playlistId) {
        return data.stream()
                .filter(playlist -> playlist.getPlaylistId() == playlistId)
                .findFirst()
                .orElse(null);
    }

    public Playlist getPlaylistByName(String name) {
        return data.stream()
                .filter(playlist -> playlist.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public PlaylistEnum getPlaylistStatus(int playlistId) {
        Playlist playlist = getPlaylistById(playlistId);
        return (playlist != null) ? playlist.getStatus() : null;
    }

    public void setPlaylistStatus(int playlistId, PlaylistEnum status) {
        Playlist playlist = getPlaylistById(playlistId);
        if (playlist != null) {
            playlist.setStatus(status);
            jsons.saveToJson(filePath, data);
        }
    }

    public Playlist getTemporaryPlaylistOfCurrentUser(UserService userService) {
        int currentUserId = userService.getCurrentUserId();
        return data.stream()
                .filter(playlist -> "temporaryPlaylist".equalsIgnoreCase(playlist.getName())
                        && playlist.getOwnerId() == currentUserId)
                .findFirst()
                .orElse(null);
    }
}
