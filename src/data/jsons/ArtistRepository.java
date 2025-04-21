package data.jsons;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.Artist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {
    private final String filePath;
    private final ObjectMapper objectMapper;

    public ArtistRepository(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    public ArtistRepository() {
        this.filePath = "src/data/jsons/artist.json";
        this.objectMapper = new ObjectMapper();
    }

    public List<Artist> getAllArtists() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            System.out.println("The JSON file is empty or does not exist.");
            return new ArrayList<>();
        }
        try {
            List<Artist> artists = objectMapper.readValue(file, new TypeReference<List<Artist>>() {});
            if (artists.isEmpty()) {
                return new ArrayList<>();
            }
            return artists;
        } catch (IOException e) {
            System.err.println("Error while loading artists: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAllArtists(List<Artist> artists) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), artists);
        } catch (IOException e) {
            System.err.println("Error during the saving action for the artists : " + e.getMessage());
        }
    }

    public void addArtist(Artist artist) {
        List<Artist> artists = getAllArtists();
        artists.add(artist);
        saveAllArtists(artists);
    }

    public void removeArtistById(int artistId) {
        List<Artist> artists = getAllArtists();
        artists.removeIf(artist -> artist.getArtistId() == artistId);
        saveAllArtists(artists);
    }

    public Artist findArtistById(int artistId) {
        return getAllArtists().stream()
                .filter(artist -> artist.getArtistId() == artistId)
                .findFirst()
                .orElse(null);
    }

    public void updateArtist(Artist updatedArtist) {
        List<Artist> artists = getAllArtists();
        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getArtistId() == updatedArtist.getArtistId()) {
                artists.set(i, updatedArtist);
                saveAllArtists(artists);
                return;
            }
        }
        System.err.println("Artist with ID " + updatedArtist.getArtistId() + " not found.");
    }

    public Artist findArtistByName(String name) {
        List<Artist> artists = getAllArtists();
        for (Artist artist : artists) {
            if (artist.getArtistName().equalsIgnoreCase(name)) {
                return artist;
            }
        }
        return null;
    }
}