package clientSide.repoFront;

import clientSide.socket.SocketClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import middle.IPlaylistRepository;
import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FrontPlaylistRepo implements IPlaylistRepository {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Playlist getPlaylistById(int playlistId) {
        return getPlaylistFromServer(Map.of(
                "command", "getPlaylistById",
                "username", "marion",
                "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                "playlistId", playlistId
        ));
    }

    @Override
    public Playlist getPlaylistByName(String name) {
        return getPlaylistFromServer(Map.of(
                "command", "getPlaylistByName",
                "username", "marion",
                "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                "name", name
        ));
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        try {
            Map<String, Object> response = SocketClient.sendRequest(Map.of(
                    "command", "getAllPlaylists",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU="
            ));

            if (!"OK".equals(response.get("status"))) return null;

            List<?> raw = (List<?>) response.get("playlists");
            String json = mapper.writeValueAsString(raw);
            return Arrays.asList(mapper.readValue(json, Playlist[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePlaylistById(int playlistId) {
        SocketClient.sendRequest(Map.of(
                "command", "deletePlaylistById",
                "username", "marion",
                "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                "playlistId", playlistId
        ));
    }

    @Override
    public void savePlaylist(Playlist playlist) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "savePlaylist",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                    "playlist", mapper.convertValue(playlist, Map.class)
            );
            SocketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlaylistEnum getPlaylistStatus(Playlist playlist) {
        try {
            Map<String, Object> response = SocketClient.sendRequest(Map.of(
                    "command", "getPlaylistStatus",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                    "playlist", mapper.convertValue(playlist, Map.class)
            ));
            return PlaylistEnum.valueOf((String) response.get("playlistStatus"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Playlist getTemporaryPlaylistOfCurrentUser(int currentUserId) {
        return getPlaylistFromServer(Map.of(
                "command", "getTemporaryPlaylistOfCurrentUser",
                "username", "marion",
                "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU="
        ));
    }

    private Playlist getPlaylistFromServer(Map<String, Object> request) {
        try {
            Map<String, Object> response = SocketClient.sendRequest(request);
            if (!"OK".equals(response.get("status"))) return null;
            Object playlistObj = response.get("playlist");
            String json = mapper.writeValueAsString(playlistObj);
            return mapper.readValue(json, Playlist.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
