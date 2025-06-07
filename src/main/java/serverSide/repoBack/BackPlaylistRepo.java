package serverSide.repoBack;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.repository.*;
import common.entities.Playlist;
import common.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackPlaylistRepo {
    private final ObjectMapper mapper = new ObjectMapper();
    private final IPlaylistRepository playlistRepo;
    private final IUserRepository userRepo;

    public BackPlaylistRepo(IPlaylistRepository playlistRepo, IUserRepository userRepo) {
        this.playlistRepo = playlistRepo;
        this.userRepo = userRepo;
    }

    public String handleRequest(Map<String, Object> request) {
        try {
            String command = (String) request.get("command");
            String username = (String) request.get("userPseudonym");

            User user = userRepo.getUserByPseudonym(username);

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

                case "updateOrInsertPlaylist" -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> playlistData = (Map<String, Object>) request.get("playlist");
                    Playlist playlist = mapper.convertValue(playlistData, Playlist.class);
                    playlistRepo.updateOrInsertPlaylist(playlist);
                    return "{\"status\": \"OK\"}";
                }

                case "getPlaylistStatus" -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> playlistData = (Map<String, Object>) request.get("playlist");
                    Playlist playlist = mapper.convertValue(playlistData, Playlist.class);
                    return mapper.writeValueAsString(Map.of("status", "OK", "playlistStatus",
                            playlistRepo.getPlaylistStatus(playlist)));
                }

                case "getTemporaryPlaylistOfCurrentUser" -> {
                    int userId = user.getUserId();
                    Playlist temp = playlistRepo.getTemporaryPlaylistOfCurrentUser(userId);

                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "OK");
                    response.put("playlist", temp); // ici c'est OK si temp == null

                    return mapper.writeValueAsString(response);
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
