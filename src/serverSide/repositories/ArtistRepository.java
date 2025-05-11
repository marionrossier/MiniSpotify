package serverSide.repositories;

import serverSide.StockageService;
import serverSide.entities.Artist;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {
    private final String filePath;
    private final StockageService stockageService;
    private List<Artist> data;

    public ArtistRepository(String filePath) {
        this.filePath = filePath;
        this.stockageService = new StockageService();
        this.data = stockageService.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public ArtistRepository() {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/artist.json");
    }

    public List<Artist> getAllArtists() {
        return new ArrayList<>(data); // retourne une copie pour éviter modifications directes
    }

    public void addArtist(Artist artist) {
        data.add(artist);
        stockageService.saveToJson(filePath, data);
    }

    public void deleteArtistById(int artistId) {
        data.removeIf(artist -> artist.getArtistId() == artistId);
        stockageService.saveToJson(filePath, data);
    }

    public Artist getArtistById(int artistId) {
        return data.stream()
                .filter(artist -> artist.getArtistId() == artistId)
                .findFirst()
                .orElse(null);
    }

    public void saveArtist(Artist artist) {
        data.add(artist);
        stockageService.saveToJson(filePath, data);
    }

    public void updateArtist(Artist updatedArtist) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getArtistId() == updatedArtist.getArtistId()) {
                data.set(i, updatedArtist);
                stockageService.saveToJson(filePath, data);
                return;
            }
        }
        System.err.println("Artist with ID " + updatedArtist.getArtistId() + " not found.");
    }

    public Artist getArtistByName(String name) {
        return data.stream()
                .filter(artist -> artist.getArtistName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
