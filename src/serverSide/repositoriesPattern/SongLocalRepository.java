package serverSide.repositoriesPattern;

import serverSide.StockageService;
import serverSide.entities.MusicGender;
import serverSide.entities.Song;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SongLocalRepository {
    private final String filePath;
    private final StockageService stockageService;
    private final ArtistLocalRepository artistLocalRepository;
    private List<Song> data;

    public SongLocalRepository(String filePath, StockageService stockageService, ArtistLocalRepository artistLocalRepository) {
        this.filePath = filePath;
        this.stockageService = stockageService;
        this.artistLocalRepository = artistLocalRepository;
        this.data = stockageService.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public SongLocalRepository(StockageService stockageService, ArtistLocalRepository artistLocalRepository) {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/song.json",
                stockageService, artistLocalRepository);

    }

    public List<Song> getAllSongs() {
        return new ArrayList<>(data);
    }

    public void addSong(Song song) {
        data.add(song);
        stockageService.saveToJson(filePath, data);
    }

    public void removeSongById(int songId) {
        data.removeIf(song -> song.getSongId() == songId);
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

    public String getSongFilePath() {
        return filePath;
    }
}
