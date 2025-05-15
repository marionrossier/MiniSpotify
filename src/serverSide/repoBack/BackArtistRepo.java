package serverSide.repoBack;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.IArtistRepository;
import serverSide.entities.Artist;
import serverSide.entities.User;
import serverSide.repoLocal.ArtistLocalRepository;
import serverSide.repoLocal.UserLocalRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackArtistRepo {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final IArtistRepository artistRepo = new ArtistLocalRepository();
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

            switch (command) {
                case "getAllArtists" -> {
                    List<Artist> all = artistRepo.getAllArtists();
                    return mapper.writeValueAsString(Map.of("status", "OK", "artists", all));
                }

                case "getArtistById" -> {
                    int id = (int) request.get("artistId");
                    Artist artist = artistRepo.getArtistById(id);
                    return artist != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "artist", artist))
                            : "{\"status\": \"ERROR\", \"message\": \"Artist not found\"}";
                }

                case "getArtistByName" -> {
                    String name = (String) request.get("artistName");
                    Artist artist = artistRepo.getArtistByName(name);
                    return artist != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "artist", artist))
                            : "{\"status\": \"ERROR\", \"message\": \"Artist not found\"}";
                }

                case "getArtistBySongId" -> {
                    int songId = (int) request.get("songId");
                    Artist artist = artistRepo.getArtistBySongId(songId);
                    return artist != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "artist", artist))
                            : "{\"status\": \"ERROR\", \"message\": \"Artist not found for song\"}";
                }

                case "addArtist", "saveArtist" -> {
                    Map<String, Object> artistMap = (Map<String, Object>) request.get("artist");
                    Artist artist = mapper.convertValue(artistMap, Artist.class);
                    if ("addArtist".equals(command)) {
                        artistRepo.addArtist(artist);
                    } else {
                        artistRepo.saveArtist(artist);
                    }
                    return "{\"status\": \"OK\"}";
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
