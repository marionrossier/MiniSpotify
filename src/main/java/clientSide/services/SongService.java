package clientSide.services;

import common.entities.MusicGender;
import common.entities.Song;
import common.repository.ISongRepository;

import java.util.LinkedList;

public class SongService {

    private final ISongRepository songRepository;

    // Constructor
    public SongService(ToolBoxService toolBoxService) {
        this.songRepository = toolBoxService.songRepository;
    }

    public void setCurrentSongId (int songId){
        Cookies.setCurrentSongId(songId);
    }

    public int getCurrentSongId() {
        return Cookies.getInstance().getCurrentSongId();
    }

    public Song getSongById(int songId) {
        return songRepository.getSongById(songId);
    }

    public LinkedList<Song> getSongByTitle(String songTitle) {
        return songRepository.getSongsByTitle(songTitle);
    }

    public LinkedList<Song> getSongsByArtist(String artistName) {
        return songRepository.getSongsByArtist(artistName);
    }

    public LinkedList<Song> getSongsByGender(MusicGender genderName) {
        return songRepository.getSongsByGender(genderName);
    }
}
