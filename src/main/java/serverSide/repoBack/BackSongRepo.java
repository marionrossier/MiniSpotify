package serverSide.repoBack;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.repository.*;
import common.entities.MusicGender;
import common.entities.Song;
import common.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackSongRepo {
    private final ObjectMapper mapper = new ObjectMapper();
    private final ISongRepository songRepo;
    private final IUserRepository userRepo;

    public BackSongRepo(ISongRepository songRepo, IUserRepository userRepo) {
        this.songRepo = songRepo;
        this.userRepo = userRepo;
    }

    public String handleRequest(Map<String, Object> request) {
        try {
            String command = (String) request.get("command");

            switch (command) {
                case "getAllSongs" -> {
                    List<Song> all = songRepo.getAllSongs();
                    return mapper.writeValueAsString(Map.of("status", "OK", "songs", all));
                }

                case "getSongById" -> {
                    int id = (int) request.get("songId");
                    Song song = songRepo.getSongById(id);
                    return song != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "song", song))
                            : "{\"status\": \"ERROR\", \"message\": \"Song not found\"}";
                }

                case "getSongsByTitle" -> {
                    String title = (String) request.get("title");
                    List<Song> songs = songRepo.getSongsByTitle(title);
                    return mapper.writeValueAsString(Map.of("status", "OK", "songs", songs));
                }

                case "getSongsByArtist" -> {
                    String artistName = (String) request.get("artistName");
                    List<Song> songs = songRepo.getSongsByArtist(artistName);
                    return mapper.writeValueAsString(Map.of("status", "OK", "songs", songs));
                }

                case "getSongsByGender" -> {
                    String gender = (String) request.get("gender");
                    List<Song> songs = songRepo.getSongsByGender(Enum.valueOf(MusicGender.class, gender));
                    return mapper.writeValueAsString(Map.of("status", "OK", "songs", songs));
                }

                case "addSong" -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> songMap = (Map<String, Object>) request.get("song");
                    Song song = mapper.convertValue(songMap, Song.class);
                    songRepo.addSong(song);
                    return "{\"status\": \"OK\"}";
                }

                default -> {
                    return "{\"status\": \"ERROR\", \"message\": \"Unknown command (song)\"}";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"ERROR\", \"message\": \"Internal server error\"}";
        }
    }
}
