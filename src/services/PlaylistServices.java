package services;

import data.entities.Playlist;
import data.entities.Song;
import data.entities.User;

import java.util.*;

public class PlaylistServices {
    private Playlist playlist;
    private final LinkedList<Song> playlistSongs;
    double playlistDuration = playlist.getPlaylistDuration();

    public PlaylistServices(Playlist playlist, LinkedList<Song> playlistSongs) {
        this.playlist = playlist;
        this.playlistSongs = playlistSongs;
    }
    public void deletePlaylist(String playlistName) {/*TODO*/}
    public void createPlaylist(String playlistName) {/*TODO*/}

    public void addSongToPlaylist(String playlistName) {/*TODO*/}
    public void editPlayList(String playlistName) {/*TODO*/}
    public void removeSongFromPlaylist(String playlistName) {/*TODO*/}
    public void reorderSongsInPlaylist(String playlistName) {/*TODO*/}
    public LinkedList<Playlist> getSharedPlaylist(User followedUser) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }
}
