package serverSide.repoBack;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.ISongRepository;
import serverSide.entities.Playlist;
import serverSide.entities.Song;
import serverSide.entities.User;
import serverSide.repoLocal.SongLocalRepository;
import serverSide.repoLocal.ArtistLocalRepository;
import serverSide.repoLocal.UserLocalRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackSongRepo {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ISongRepository songRepo =
            new SongLocalRepository(new serverSide.StockageService(), new ArtistLocalRepository());
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
                    List<Song> songs = songRepo.getSongsByGender(Enum.valueOf(serverSide.entities.MusicGender.class, gender));
                    return mapper.writeValueAsString(Map.of("status", "OK", "songs", songs));
                }

                case "addSong" -> {
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
