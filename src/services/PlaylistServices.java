package services;

import datas.entities.Playlist;
import datas.entities.Song;
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
