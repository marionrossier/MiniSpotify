package serverSide.repositoriesPattern;

import serverSide.StockageService;
import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import clientSide.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class PlaylistLocalRepository {
    private final String filePath;
    private final StockageService stockageService;
    private List<Playlist> data;

    public PlaylistLocalRepository(String filePath) {
        this.filePath = filePath;
        this.stockageService = new StockageService();
        this.data = stockageService.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public PlaylistLocalRepository() {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/playlist.json");
    }

    public List<Playlist> getAllPlaylists() {
        return new ArrayList<>(data); // copie défensive
    }

    public void savePlaylist(Playlist playlist) {
        data.removeIf(p -> p.getPlaylistId() == playlist.getPlaylistId());
        data.add(playlist);
        stockageService.saveToJson(filePath, data);
    }

    public void deletePlaylistById(int playlistId) {
        data.removeIf(playlist -> playlist.getPlaylistId() == playlistId);
        stockageService.saveToJson(filePath, data);
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
            stockageService.saveToJson(filePath, data);
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
