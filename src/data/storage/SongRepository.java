package data.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    private final String filePath;
    private final ObjectMapper objectMapper;

    public SongRepository(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    public SongRepository(){
        this.filePath = "src/data/storage/song.json";
        this.objectMapper = new ObjectMapper();
    }

    public List<Song> getAllSongs() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Song>>() {});
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des chansons : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAllSongs(List<Song> songs) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), songs);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des chansons : " + e.getMessage());
        }
    }

    public void addSong(Song song) {
        List<Song> songs = getAllSongs();
        songs.add(song);
        saveAllSongs(songs);
    }

    public void removeSongById(int songId) {
        List<Song> songs = getAllSongs();
        songs.removeIf(song -> song.getSongId() == songId);
        saveAllSongs(songs);
    }

    public Song findSongById(int songId) {
        return getAllSongs().stream()
                .filter(song -> song.getSongId() == songId)
                .findFirst()
                .orElse(null);
    }
}
