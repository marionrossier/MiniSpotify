package clientSide.repoFront;

import clientSide.socket.SocketClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import serverSide.entities.Artist;
import middle.IArtistRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FrontArtistRepo implements IArtistRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private final SocketClient socketClient;

    public FrontArtistRepo(SocketClient socketClient) {
        this.socketClient = new SocketClient();
    }

    @Override
    public List<Artist> getAllArtists() {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getAllArtists",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU="
            ));

            if (!"OK".equals(response.get("status"))) return null;

            List<?> raw = (List<?>) response.get("artists");
            String json = mapper.writeValueAsString(raw);
            return Arrays.asList(mapper.readValue(json, Artist[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addArtist(Artist artist) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "addArtist",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                    "artist", mapper.convertValue(artist, Map.class)
            );
            socketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artist getArtistById(int artistId) {
        return getArtistFromServer(Map.of(
                "command", "getArtistById",
                "username", "marion",
                "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                "artistId", artistId
        ));
    }

    @Override
    public void saveArtist(Artist artist) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "saveArtist",
                    "username", "marion",
                    "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                    "artist", mapper.convertValue(artist, Map.class)
            );
            socketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artist getArtistByName(String name) {
        return getArtistFromServer(Map.of(
                "command", "getArtistByName",
                "username", "marion",
                "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                "artistName", name
        ));
    }

    @Override
    public Artist getArtistBySongId(int songId) {
        return getArtistFromServer(Map.of(
                "command", "getArtistBySongId",
                "username", "marion",
                "password", "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=",
                "songId", songId
        ));
    }

    private Artist getArtistFromServer(Map<String, Object> request) {
        try {
            Map<String, Object> response = socketClient.sendRequest(request);
            if (!"OK".equals(response.get("status"))) return null;
            Object artistObj = response.get("artist");
            String json = mapper.writeValueAsString(artistObj);
            return mapper.readValue(json, Artist.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
