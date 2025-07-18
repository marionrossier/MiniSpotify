package common.repository;

import common.entities.MusicGender;
import common.entities.Song;

import java.util.ArrayList;
import java.util.LinkedList;

public interface ISongRepository {

    ArrayList<Song> getAllSongs();
    void addSong(Song song);
    Song getSongById(int songId);
    LinkedList<Song> getSongsByTitle(String title);
    LinkedList<Song> getSongsByArtist(String artistName);
    LinkedList<Song> getSongsByGender(MusicGender gender);
}
