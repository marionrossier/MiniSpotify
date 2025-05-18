package clientSide.services;

import common.entities.MusicGender;
import common.entities.Song;
import common.*;

import java.util.LinkedList;

public class SongService {

    private final ISongRepository songLocalRepository;

    // Constructor
    public SongService(ToolBoxService toolBoxService) {
        this.songLocalRepository = toolBoxService.songLocalRepository;
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

    public LinkedList<Song> getSongByTitle(String songTitle) {
        return songLocalRepository.getSongsByTitle(songTitle);
    }

    public LinkedList<Song> getSongsByArtist(String artistName) {
        return songLocalRepository.getSongsByArtist(artistName);
    }

    public LinkedList<Song> getSongsByGender(MusicGender genderName) {
        return songLocalRepository.getSongsByGender(genderName);
    }
}
