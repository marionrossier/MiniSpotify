package serverSide.repoBack;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.IPlaylistRepository;
import serverSide.entities.Playlist;
import serverSide.repoLocal.PlaylistLocalRepository;
import serverSide.repoLocal.UserLocalRepository;
import serverSide.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackPlaylistRepo {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final IPlaylistRepository playlistRepo = new PlaylistLocalRepository();
    private static final UserLocalRepository userRepo = new UserLocalRepository();

    public static String handleRequest(Map<String, Object> request) {
        try {
            String command = (String) request.get("command");
            String username = (String) request.get("username");
            String passwordHash = (String) request.get("password");

            Optional<User> optUser = userRepo.authenticate(username, passwordHash);
            if (optUser.isEmpty()) {
                return "{\"status\": \"ERROR\", \"message\": \"Authentication failed\"}";
            }

            User user = optUser.get();

            switch (command) {
                case "getPlaylistById" -> {
                    int playlistId = (int) request.get("playlistId");
                    Playlist playlist = playlistRepo.getPlaylistById(playlistId);
                    return playlist != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "playlist", playlist))
                            : "{\"status\": \"ERROR\", \"message\": \"Playlist not found\"}";
                }

                case "getPlaylistByName" -> {
                    String name = (String) request.get("name");
                    Playlist playlist = playlistRepo.getPlaylistByName(name);
                    return playlist != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "playlist", playlist))
                            : "{\"status\": \"ERROR\", \"message\": \"Playlist not found\"}";
                }

                case "getAllPlaylists" -> {
                    List<Playlist> all = playlistRepo.getAllPlaylists();
                    return mapper.writeValueAsString(Map.of("status", "OK", "playlists", all));
                }

                case "deletePlaylistById" -> {
                    int playlistId = (int) request.get("playlistId");
                    playlistRepo.deletePlaylistById(playlistId);
                    return "{\"status\": \"OK\"}";
                }

                case "savePlaylist" -> {
                    Map<String, Object> playlistData = (Map<String, Object>) request.get("playlist");
                    Playlist playlist = mapper.convertValue(playlistData, Playlist.class);
                    playlistRepo.savePlaylist(playlist);
                    return "{\"status\": \"OK\"}";
                }

                case "getPlaylistStatus" -> {
                    Map<String, Object> playlistData = (Map<String, Object>) request.get("playlist");
                    Playlist playlist = mapper.convertValue(playlistData, Playlist.class);
                    return mapper.writeValueAsString(Map.of("status", "OK", "playlistStatus",
                            playlistRepo.getPlaylistStatus(playlist)));
                }

                case "getTemporaryPlaylistOfCurrentUser" -> {
                    Playlist temp = playlistRepo.getTemporaryPlaylistOfCurrentUser(user.getUserId());
                    return temp != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "playlist", temp))
                            : "{\"status\": \"ERROR\", \"message\": \"No temporary playlist\"}";
                }

                default -> {
                    return "{\"status\": \"ERROR\", \"message\": \"Unknown command\"}";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"ERROR\", \"message\": \"Internal server error\"}";
        }
    }

}
