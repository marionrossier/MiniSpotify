package services;

import data.entities.Song;
import data.jsons.SongRepository;
import java.util.Scanner;

public class SongService {

    Scanner in = new Scanner(System.in);
    private final Icon icon = new Icon();
    public final SongRepository songRepository;
    private final PrintService printService = new PrintService();

    // Constructor
    public SongService(SongRepository songRepo) {
        this.songRepository = songRepo;
    }

    public void setCurrentSongId (int songId){
        Cookies_SingletonPattern.setCurrentSongId(songId);
    }

    public int getCurrentSongId() {
        return Cookies_SingletonPattern.getInstance().getCurrentSongId();
    }

    public Song getSongById(int songId) {
        return songRepository.getSongById(songId);
    }
}
