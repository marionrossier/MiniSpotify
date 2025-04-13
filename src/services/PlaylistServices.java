package services;

import data.entities.Playlist;
import data.entities.Song;
import java.util.*;

public class PlaylistServices {
    private Playlist playlist;
    private LinkedList<Song> playlistSongs;
    double playlistDuration = playlist.getPlaylistDuration();

    public PlaylistServices(Playlist playlist, LinkedList<Song> playlistSongs) {
        this.playlist = playlist;
        this.playlistSongs = playlistSongs;
    }
}
