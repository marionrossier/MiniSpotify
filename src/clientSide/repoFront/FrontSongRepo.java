package clientSide.repoFront;

import serverSide.entities.MusicGender;
import serverSide.entities.Song;
import middle.ISongRepository;

import java.util.LinkedList;
import java.util.List;

public class FrontSongRepo implements ISongRepository {
    @Override
    public List<Song> getAllSongs() {
        return List.of();
    }

    @Override
    public void addSong(Song song) {

    }

    @Override
    public Song getSongById(int songId) {
        return null;
    }

    @Override
    public LinkedList<Song> getSongsByTitle(String title) {
        return null;
    }

    @Override
    public LinkedList<Song> getSongsByArtist(String artistName) {
        return null;
    }

    @Override
    public LinkedList<Song> getSongsByGender(MusicGender gender) {
        return null;
    }
}
