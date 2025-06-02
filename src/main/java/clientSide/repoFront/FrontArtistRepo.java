package clientSide.repoFront;

import com.fasterxml.jackson.databind.ObjectMapper;

import clientSide.services.*;
import clientSide.socket.*;
import common.entities.Artist;
import common.repository.IArtistRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FrontArtistRepo implements IArtistRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private final SocketClient socketClient;

    public FrontArtistRepo(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public List<Artist> getAllArtists() {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getAllArtists",
                    "userPseudonym", Cookies.getInstance().getUserPseudonym(),
                    "password", Cookies.getInstance().getUserPassword()
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
                    "userPseudonym", Cookies.getInstance().getUserPseudonym(),
                    "password", Cookies.getInstance().getUserPassword(),
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
                "userPseudonym", Cookies.getInstance().getUserPseudonym(),
                "password", Cookies.getInstance().getUserPassword(),
                "artistId", artistId
        ));
    }

    @Override
    public void updateOrInsertArtist(Artist artist) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "updateOrInsertArtist",
                    "userPseudonym", Cookies.getInstance().getUserPseudonym(),
                    "password", Cookies.getInstance().getUserPassword(),
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
                "userPseudonym", Cookies.getInstance().getUserPseudonym(),
                "password", Cookies.getInstance().getUserPassword(),
                "artistName", name
        ));
    }

    @Override
    public Artist getArtistBySongId(int songId) {
        return getArtistFromServer(Map.of(
                "command", "getArtistBySongId",
                "userPseudonym", Cookies.getInstance().getUserPseudonym(),
                "password", Cookies.getInstance().getUserPassword(),
                "songId", songId
        ));
    }

    private Artist getArtistFromServer(Map<String, Object> request) {
        try {
            Map<String, Object> response = socketClient.sendRequest(request);
            if (!response.get("status").equals("OK")) {
                System.out.println(response);
                return null;
            }
            Object artistObj = response.get("artist");
            String json = mapper.writeValueAsString(artistObj);
            return mapper.readValue(json, Artist.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
