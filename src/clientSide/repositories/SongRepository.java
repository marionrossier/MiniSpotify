package clientSide.repositories;

import clientSide.entities.MusicGender;
import clientSide.entities.Song;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SongRepository {
    private final String filePath;
    private final Jsons jsons;
    private List<Song> data;

    public SongRepository(String filePath) {
        this.filePath = filePath;
        this.jsons = new Jsons();
        this.data = jsons.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public SongRepository() {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/song.json");
    }

    public List<Song> getAllSongs() {
        return new ArrayList<>(data);
    }

    public void addSong(Song song) {
        data.add(song);
        jsons.saveToJson(filePath, data);
    }

    public void removeSongById(int songId) {
        data.removeIf(song -> song.getSongId() == songId);
        jsons.saveToJson(filePath, data);
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

    public LinkedList<Song> getSongsByArtist(String artist) {
        return new LinkedList<>(data.stream()
                .filter(song -> song.getArtist().getArtistName().toLowerCase().contains(artist.toLowerCase()))
                .toList());
    }

    public LinkedList<Song> getSongsByGender(MusicGender gender) {
        return new LinkedList<>(data.stream()
                .filter(song -> song.getGender() == gender)
                .toList());
    }

    public String getFilePath() {
        return filePath;
    }
}
