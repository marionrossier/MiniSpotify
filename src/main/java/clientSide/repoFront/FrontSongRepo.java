package clientSide.repoFront;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import clientSide.socket.SocketClient;
import common.entities.MusicGender;
import common.entities.Song;
import common.repository.ISongRepository;

import java.util.*;

public class FrontSongRepo implements ISongRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final SocketClient socketClient;

    public FrontSongRepo(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public ArrayList<Song> getAllSongs() {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getAllSongs"
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
                    "song", mapper.convertValue(song, Map.class)
            );
            socketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Song getSongById(int songId) {
        return getSongFromServer(Map.of(
                "command", "getSongById",
                "songId", songId
        ));
    }

    @Override
    public LinkedList<Song> getSongsByTitle(String title) {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getSongsByTitle",
                    "title", title
            ));

            return getResponse(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LinkedList<Song> getSongsByArtist(String artistName) {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getSongsByArtist",
                    "artistName", artistName
            ));

            return getResponse(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LinkedList<Song> getSongsByGender(MusicGender gender) {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getSongsByGender",
                    "gender", gender.toString()
            ));

            return getResponse(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LinkedList<Song> getResponse(Map<String, Object> response) throws JsonProcessingException {
        if (!"OK".equals(response.get("status"))) return null;

        List<?> raw = (List<?>) response.get("songs");
        String json = mapper.writeValueAsString(raw);
        Song[] songs = mapper.readValue(json, Song[].class);
        return new LinkedList<>(Arrays.asList(songs));
    }

    private Song getSongFromServer(Map<String, Object> request) {
        try {
            Map<String, Object> response = socketClient.sendRequest(request);
            if (!response.get("status").equals("OK")) {
                System.out.println(response);
                return null;
            }
            Object songObj = response.get("song");
            String json = mapper.writeValueAsString(songObj);
            return mapper.readValue(json, Song.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
