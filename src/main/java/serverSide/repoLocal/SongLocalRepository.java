package serverSide.repoLocal;

import common.entities.MusicGender;
import common.entities.Song;
import common.repository.IArtistRepository;
import common.repository.ISongRepository;
import serverSide.StockageService;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SongLocalRepository implements ISongRepository {
    private final String filePath;
    private final StockageService stockageService;
    private final IArtistRepository artistLocalRepository;
    private final List<Song> data;

    public SongLocalRepository(String filePath, StockageService stockageService, IArtistRepository artistLocalRepository) {
        this.filePath = filePath;
        this.artistLocalRepository = artistLocalRepository;
        this.stockageService = stockageService;
        this.stockageService.copyResourceToWritableLocation("jsons/song.json");

        this.data = stockageService.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public SongLocalRepository(StockageService stockageService, IArtistRepository artistLocalRepository) {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/song.json",
                stockageService, artistLocalRepository);
    }

    public ArrayList<Song> getAllSongs() {
        return new ArrayList<>(data);
    }

    public void addSong(Song song) { //only for Populate
        data.add(song);
        stockageService.saveToJson(filePath, data);
    }

    public Song getSongById(int songId) {
        return data.stream()
                .filter(song -> song.getSongId() == songId)
                .findFirst()
                .orElse(null);
    }

    public LinkedList<Song> getSongsByTitle(String title) {
        return new LinkedList<>(data.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList());
    }

    public LinkedList<Song> getSongsByArtist(String artistName) {
        return new LinkedList<>(data.stream()
                .filter(song -> artistLocalRepository.getArtistById(song.getArtistId()).getArtistName().toLowerCase().contains(artistName.toLowerCase()))
                .toList());
    }

    public LinkedList<Song> getSongsByGender(MusicGender gender) {
        return new LinkedList<>(data.stream()
                .filter(song -> song.getGender() == gender)
                .toList());
    }
}
