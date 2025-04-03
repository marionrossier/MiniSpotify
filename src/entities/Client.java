package entities;

import java.util.*;

public class Client {
    private User user;

    public User createAccount() {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }
    public User updateAccount() {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }

    public void followUser() {/*TODO*/}
    public void unfollowUser() {/*TODO*/}

    public LinkedList<Playlist> getSharedPlaylist(User followedUser) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }

    public Song searchSong() {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }

    public void deletePlaylist(String playlistName) {/*TODO*/}
    public void createPlaylist(String playlistName) {/*TODO*/}

    public void addSongToPlaylist(String playlistName) {/*TODO*/}
    public void editPlayList(String playlistName) {/*TODO*/}
    public void removeSongFromPlaylist(String playlistName) {/*TODO*/}
    public void reorderSongsInPlaylist(String playlistName) {/*TODO*/}
}
