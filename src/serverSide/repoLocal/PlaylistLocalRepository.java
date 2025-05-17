package serverSide.repoLocal;

import commun.IPlaylistRepository;
import commun.StockageService;
import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class PlaylistLocalRepository implements IPlaylistRepository {
    private final String filePath;
    private final StockageService stockageService;
    private final List<Playlist> data;

    public PlaylistLocalRepository(String filePath) {
        this.filePath = filePath;
        this.stockageService = new StockageService();
        stockageService.copyResourceToWritableLocation("jsons/playlist.json");

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

    public PlaylistEnum getPlaylistStatus(Playlist playlist) {
        return (playlist != null) ? playlist.getStatus() : null;
    }

    public Playlist getTemporaryPlaylistOfCurrentUser(int currentUserId) {
        return data.stream()
                .filter(playlist -> "temporaryPlaylist".equalsIgnoreCase(playlist.getName())
                        && playlist.getOwnerId() == currentUserId)
                .findFirst()
                .orElse(null);
    }
}
