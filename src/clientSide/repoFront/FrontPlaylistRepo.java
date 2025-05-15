package clientSide.repoFront;

import clientSide.services.Cookies_SingletonPattern;
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
    private final SocketClient socketClient;

    public FrontPlaylistRepo(SocketClient socketClient) {
        this.socketClient = new SocketClient();
    }

    @Override
    public Playlist getPlaylistById(int playlistId) {
        return getPlaylistFromServer(Map.of(
                "command", "getPlaylistById",
                "userPseudonym", Cookies_SingletonPattern.getInstance().getUserPseudonym(),
                "password", Cookies_SingletonPattern.getInstance().getUserPassword(),
                "playlistId", playlistId
        ));
    }

    @Override
    public Playlist getPlaylistByName(String name) {
        return getPlaylistFromServer(Map.of(
                "command", "getPlaylistByName",
                "userPseudonym", Cookies_SingletonPattern.getInstance().getUserPseudonym(),
                "password", Cookies_SingletonPattern.getInstance().getUserPassword(),
                "name", name
        ));
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getAllPlaylists",
                    "userPseudonym", Cookies_SingletonPattern.getInstance().getUserPseudonym(),
                    "password", Cookies_SingletonPattern.getInstance().getUserPassword()
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
        socketClient.sendRequest(Map.of(
                "command", "deletePlaylistById",
                "userPseudonym", Cookies_SingletonPattern.getInstance().getUserPseudonym(),
                "password", Cookies_SingletonPattern.getInstance().getUserPassword(),
                "playlistId", playlistId
        ));
    }

    @Override
    public void savePlaylist(Playlist playlist) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "savePlaylist",
                    "userPseudonym", Cookies_SingletonPattern.getInstance().getUserPseudonym(),
                    "password", Cookies_SingletonPattern.getInstance().getUserPassword(),
                    "playlist", mapper.convertValue(playlist, Map.class)
            );
            socketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlaylistEnum getPlaylistStatus(Playlist playlist) {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getPlaylistStatus",
                    "userPseudonym", Cookies_SingletonPattern.getInstance().getUserPseudonym(),
                    "password", Cookies_SingletonPattern.getInstance().getUserPassword(),
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
                "userPseudonym", Cookies_SingletonPattern.getInstance().getUserPseudonym(),
                "password", Cookies_SingletonPattern.getInstance().getUserPassword()
        ));
    }

    private Playlist getPlaylistFromServer(Map<String, Object> request) {
        try {
            Map<String, Object> response = socketClient.sendRequest(request);
            if (!response.get("status").equals("OK")) {
                System.out.println(response);
                return null;
            }
            Object playlistObj = response.get("playlist");
            String json = mapper.writeValueAsString(playlistObj);
            return mapper.readValue(json, Playlist.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
