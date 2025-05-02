package services;

import data.entities.Playlist;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;

import java.util.*;

public class PlaylistServices {

    UserRepository userRepository = new UserRepository();
    Scanner in = new Scanner(System.in);
    PlaylistRepository playlistRepository;

    public PlaylistServices (PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
    }

    public void deletePlaylist(int playlistId) {
        playlistRepository.deletePlaylistById(playlistId);
    }

    public void createPlaylist(String playlistName) {/*TODO*/}


    public int validationPlaylistChoice() {
        Scanner scanner = new Scanner(System.in);
        User currentUser = userRepository.getUserById(Cookies_SingletonPattern.getInstance().getUserId());

        int chosenPlaylist = -1;

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("0")) {
                return 0;
            }

            try {
                int inputNumber = Integer.parseInt(input);

                if (inputNumber < 1 || inputNumber > currentUser.getPlaylists().size()) {
                    System.err.println("Invalid Playlist number.");
                    System.out.println("Try again or press \"0\" to go back : ");
                } else {
                    chosenPlaylist = currentUser.getPlaylists().get(inputNumber - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input, please enter a number : ");
            }
        }

        return chosenPlaylist;
    }

    public void addSongToPlaylist(int songId) {
        playlistRepository.getPlaylistById(Cookies_SingletonPattern.getInstance().getCurrentPlaylistId())
                .getPlaylistSongsListWithId().add(songId);
    }

    public void addSongToPlaylistFromTemporaryPlaylist(int playlistId) {
        Playlist temporaryPlaylist = playlistRepository.getPlaylistByName("temporaryPlaylist");
        Playlist targetPlaylist = playlistRepository.getPlaylistById(playlistId);

        if (targetPlaylist != null && temporaryPlaylist != null) {
            targetPlaylist.getPlaylistSongsListWithId().addAll(temporaryPlaylist.getPlaylistSongsListWithId());
            playlistRepository.updatePlaylist(targetPlaylist);
        } else {
            System.err.println("Target playlist or temporary playlist not found.");
        }
    }

    public void editPlayListName(int playlistId, String newName) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        playlist.setPlaylistName(newName);
        playlistRepository.savePlaylist(playlist);
        System.out.println("Playlist renamed to " + newName + " !");
    }

    public void removeSongFromPlaylist(int songIndex) {
        Playlist playlist = playlistRepository.getPlaylistById(Cookies_SingletonPattern.getInstance().getCurrentPlaylistId());
        playlistRepository.getPlaylistById(playlist.getPlaylistId())
                .getPlaylistSongsListWithId().remove(songIndex);
        playlistRepository.savePlaylist(playlist);

    }

    public void reorderSongsInPlaylist(String playlistName) {/*TODO*/}
    public LinkedList<Playlist> getSharedPlaylist(User followedUser) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }
}
