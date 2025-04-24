package services;

import data.entities.Playlist;
import data.entities.Song;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;

import java.util.*;

public class PlaylistServices {

    PlaylistRepository playlistRepository = new PlaylistRepository();
    UserRepository userRepository = new UserRepository();
    User currentUser = userRepository.getUserById(Cookies_SingeltonPattern.getInstance().getUserId());

    public PlaylistServices (){

    }

    public void deletePlaylist(String playlistName) {/*TODO*/}
    public void createPlaylist(String playlistName) {/*TODO*/}

    public void printUserPlaylists(){
        int i = 1;
        for (int playlistId : currentUser.getPlaylists()) {
            Playlist playlist = playlistRepository.getPlaylistById(playlistId);
            if (playlist != null) {
                System.out.println(i + ". " + playlist.getPlaylistName());
                i++;
            }
        }
    }

    public void addSongToPlaylist(String playlistName) {/*TODO*/}
    public void editPlayList(String playlistName) {/*TODO*/}
    public void removeSongFromPlaylist(String playlistName) {/*TODO*/}
    public void reorderSongsInPlaylist(String playlistName) {/*TODO*/}
    public LinkedList<Playlist> getSharedPlaylist(User followedUser) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }
}
