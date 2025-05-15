package clientSide.repoFront;

import clientSide.socket.SocketClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import middle.ISongRepository;
import serverSide.entities.MusicGender;
import serverSide.entities.Song;

import java.util.*;

public class FrontSongRepo implements ISongRepository {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ArrayList<Song> getAllSongs() {
        try {
            Map<String, Object> response = SocketClient.sendRequest(Map.of(
                    "command", "getAllSongs",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU="
            ));

            if (!"OK".equals(response.get("status"))) return null;

            ArrayList<?> raw = (ArrayList<?>) response.get("songs");
            String json = mapper.writeValueAsString(raw);
            Song[] songsArray = mapper.readValue(json, Song[].class);

            return new ArrayList<>(Arrays.asList(songsArray));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void addSong(Song song) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "addSong",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                    "song", mapper.convertValue(song, Map.class)
            );
            SocketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Song getSongById(int songId) {
        return getSongFromServer(Map.of(
                "command", "getSongById",
                "username", "marion",
                "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                "songId", songId
        ));
    }

    @Override
    public LinkedList<Song> getSongsByTitle(String title) {
        try {
            Map<String, Object> response = SocketClient.sendRequest(Map.of(
                    "command", "getSongsByTitle",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                    "title", title
            ));

            if (!"OK".equals(response.get("status"))) return null;

            List<?> raw = (List<?>) response.get("songs");
            String json = mapper.writeValueAsString(raw);
            Song[] songs = mapper.readValue(json, Song[].class);
            return new LinkedList<>(Arrays.asList(songs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LinkedList<Song> getSongsByArtist(String artistName) {
        try {
            Map<String, Object> response = SocketClient.sendRequest(Map.of(
                    "command", "getSongsByArtist",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                    "artistName", artistName
            ));

            if (!"OK".equals(response.get("status"))) return null;

            List<?> raw = (List<?>) response.get("songs");
            String json = mapper.writeValueAsString(raw);
            Song[] songs = mapper.readValue(json, Song[].class);
            return new LinkedList<>(Arrays.asList(songs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LinkedList<Song> getSongsByGender(MusicGender gender) {
        try {
            Map<String, Object> response = SocketClient.sendRequest(Map.of(
                    "command", "getSongsByGender",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                    "gender", gender.toString()
            ));

            if (!"OK".equals(response.get("status"))) return null;

            List<?> raw = (List<?>) response.get("songs");
            String json = mapper.writeValueAsString(raw);
            Song[] songs = mapper.readValue(json, Song[].class);
            return new LinkedList<>(Arrays.asList(songs));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Song getSongFromServer(Map<String, Object> request) {
        try {
            Map<String, Object> response = SocketClient.sendRequest(request);
            if (!"OK".equals(response.get("status"))) return null;
            Object songObj = response.get("song");
            String json = mapper.writeValueAsString(songObj);
            return mapper.readValue(json, Song.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
