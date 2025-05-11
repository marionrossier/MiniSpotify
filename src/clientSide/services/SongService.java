package clientSide.services;

import serverSide.entities.Song;
import serverSide.repositories.SongLocalRepository;

public class SongService {

    public final SongLocalRepository songLocalRepository;

    // Constructor
    public SongService(SongLocalRepository songRepo) {
        this.songLocalRepository = songRepo;
    }

    public void setCurrentSongId (int songId){
        Cookies_SingletonPattern.setCurrentSongId(songId);
    }

    public int getCurrentSongId() {
        return Cookies_SingletonPattern.getInstance().getCurrentSongId();
    }

    public Song getSongById(int songId) {
        return songLocalRepository.getSongById(songId);
    }
}
