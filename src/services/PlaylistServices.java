package services;

import data.entities.Playlist;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;

import java.util.*;

public class PlaylistServices {

    Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository;
    final PlaylistRepository playlistRepository;
    private final UserService userService;


    public PlaylistServices (PlaylistRepository playlistRepository, UserRepository userRepository){
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.userService = new UserService(userRepository);

    }

    public PlaylistServices (PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
        userRepository = new UserRepository();
        this.userService = new UserService(userRepository);

    }

    public void deletePlaylist(int playlistId) {
        playlistRepository.deletePlaylistById(playlistId);
    }

    public void createPlaylist(String playlistName) {/*TODO*/}


    public int validationPlaylistChoice() {
        User currentUser = userRepository.getUserById(userService.getCurrentUserId());

        int chosenPlaylist = -1;

        while (true) {
            String input = this.scanner.nextLine();

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

    public void addSong(int currentPlaylistId, int currentSongId) {
        Playlist playlist = this.playlistRepository.getPlaylistById(currentPlaylistId);
        playlist.getPlaylistSongsListWithId().add(currentSongId);
        playlist.setPlaylistDuration();

        this.playlistRepository.savePlaylist(playlist);
    }

    public void reorderSong(int playlistId, int songId, int oldIndex, int newIndex) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        playlist.getPlaylistSongsListWithId().remove(oldIndex);
        playlist.getPlaylistSongsListWithId().add(newIndex, songId);

        playlistRepository.savePlaylist(playlist);
    }

    public void reorderSongsInPlaylist(String playlistName) {/*TODO*/}

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

    public void renamePlayList(int playlistId, String newName) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        playlist.setPlaylistName(newName);

        playlistRepository.savePlaylist(playlist);
        System.out.println("Playlist renamed to " + newName + " !");
    }

    public void removeSongFromPlaylist(int playlistId, int songIndex) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);

        playlistRepository.getPlaylistById(playlist.getPlaylistId())
                .getPlaylistSongsListWithId().remove(songIndex);

        playlistRepository.savePlaylist(playlist);

    }

    public LinkedList<Playlist> getSharedPlaylist(User followedUser) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }

    public void deleteTemporaryPlaylist() {
        Playlist temporaryPlaylist = playlistRepository.getPlaylistById(getTemporaryPlaylistId());
        if (temporaryPlaylist != null) {
            int playlistId = temporaryPlaylist.getPlaylistId();
            playlistRepository.deletePlaylistById(playlistId);
        }
    }

//    public void resetTemporaryPlaylist(){
//        Playlist temporaryPlaylist = playlistRepository.getPlaylistById(getTemporaryPlaylistId());
//        temporaryPlaylist.getPlaylistSongsListWithId().removeAll(temporaryPlaylist.getPlaylistSongsListWithId());
//    }

    public void createTemporaryPlaylistAndInitCookies(LinkedList<Integer> chosenSongs) {
        //Creation of temporaryPlaylist
        Playlist temporaryPlaylist = playlistRepository.getPlaylistByName("temporaryPlaylist");
        if (temporaryPlaylist == null) {
            temporaryPlaylist = new Playlist("temporaryPlaylist");
            playlistRepository.savePlaylist(temporaryPlaylist);
        }
        temporaryPlaylist.setPlaylistSongsId(chosenSongs);
        temporaryPlaylist.setPlaylistInformation();

        playlistRepository.savePlaylist(temporaryPlaylist);
        int temporaryPlaylistId = temporaryPlaylist.getPlaylistId();

        //Initialisation of the Cookies
        setCurrentSongId(playlistRepository
                .getPlaylistById(temporaryPlaylistId).getPlaylistSongsListWithId().getFirst());
    }

    public void createPlaylistWithTemporaryPlaylist(String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName);

        Playlist temporaryPlaylist = playlistRepository.getPlaylistByName("temporaryPlaylist");
        newPlaylist.setPlaylistSongsId(temporaryPlaylist.getPlaylistSongsListWithId());
        newPlaylist.setPlaylistInformation();
        playlistRepository.savePlaylist(newPlaylist);

        userService.addOnePlaylist(newPlaylist.getPlaylistId());

        this.deleteTemporaryPlaylist();
    }
    
    public int getCurrentPlaylistId (){
        return Cookies_SingletonPattern.getInstance().getCurrentPlaylistId();
    }

    public int getTemporaryPlaylistId (){
        return playlistRepository.getPlaylistByName("temporaryPlaylist").getPlaylistId();
    }

    public void setCurrentPlaylistId (int playlistId){
        Cookies_SingletonPattern.setCurrentPlaylistId(playlistId);
    }

    public void setCurrentSongId (int songId){
        Cookies_SingletonPattern.setCurrentSongId(songId);
    }

    public int getCurrentSongId() {
        return Cookies_SingletonPattern.getInstance().getCurrentSongId();
    }

    public int getAllSongsPlaylistId (){
        return playlistRepository.getPlaylistByName("AllSongs").getPlaylistId();
    }
}
