package middle;

import serverSide.entities.MusicGender;
import serverSide.entities.Song;

import java.util.LinkedList;
import java.util.List;

public interface ISongRepository {

    List<Song> getAllSongs();
    void addSong(Song song);
    Song getSongById(int songId);
    LinkedList<Song> getSongsByTitle(String title);
    LinkedList<Song> getSongsByArtist(String artistName);
    LinkedList<Song> getSongsByGender(MusicGender gender);
}
