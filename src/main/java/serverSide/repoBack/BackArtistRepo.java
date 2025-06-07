package serverSide.repoBack;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.repository.*;
import common.entities.Artist;

import java.util.List;
import java.util.Map;

public class BackArtistRepo {
    private final ObjectMapper mapper = new ObjectMapper();
    private final IArtistRepository artistRepo;

    public BackArtistRepo(IArtistRepository artistRepo) {
        this.artistRepo = artistRepo;
    }

    public String handleRequest(Map<String, Object> request) {
        try {
            String command = (String) request.get("command");

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

                case "addArtist", "updateOrInsertArtist" -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> artistMap = (Map<String, Object>) request.get("artist");
                    Artist artist = mapper.convertValue(artistMap, Artist.class);
                    if ("addArtist".equals(command)) {
                        artistRepo.addArtist(artist);
                    } else {
                        artistRepo.updateOrInsertArtist(artist);
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
