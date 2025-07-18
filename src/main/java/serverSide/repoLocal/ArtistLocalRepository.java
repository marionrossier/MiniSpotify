package serverSide.repoLocal;

import common.entities.Artist;
import common.repository.IArtistRepository;
import serverSide.services.StockageService;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class ArtistLocalRepository implements IArtistRepository {
    private final String filePath;
    private final StockageService stockageService;
    private final List<Artist> data;

    public ArtistLocalRepository(String filePath) {
        this.filePath = filePath;
        this.stockageService = new StockageService();

        stockageService.copyResourceToWritableLocation("jsons/artist.json");
        this.data = stockageService.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public ArtistLocalRepository() {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/artist.json");
    }

    public List<Artist> getAllArtists() {
        return new ArrayList<>(data);
    }

    public void addArtist(Artist artist) {
        data.add(artist);
        stockageService.saveToJson(filePath, data);
    }

    public Artist getArtistById(int artistId) {
        return data.stream()
                .filter(artist -> artist.getArtistId() == artistId)
                .findFirst()
                .orElse(null);
    }

    public void updateOrInsertArtist(Artist artist) {
        data.add(artist);
        stockageService.saveToJson(filePath, data);
    }

    public Artist getArtistByName(String name) {
        return data.stream()
                .filter(artist -> artist.getArtistName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Artist getArtistBySongId(int songId) {
        return data.stream()
                .filter(artist -> artist.getArtistSongsID().contains(songId))
                .findFirst()
                .orElse(null);
    }
}
