package clientSide.services;

import clientSide.entities.Song;
import clientSide.repositories.SongRepository;

public class SongService {

    public final SongRepository songRepository;

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
