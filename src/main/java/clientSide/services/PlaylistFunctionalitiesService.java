package clientSide.services;

import serverSide.entities.*;
import commun.*;
import java.util.*;

import static clientSide.services.PrintHelper.*;

public class PlaylistFunctionalitiesService {

    Scanner scanner = new Scanner(System.in);
    private final IUserRepository userLocalRepository;
    private final IPlaylistRepository playlistLocalRepository;
    private final UserService userService;
    private final SongService songService;

    public PlaylistFunctionalitiesService(ToolBoxService toolBoxService, UserService userService,
                                          SongService songService){
        this.playlistLocalRepository = toolBoxService.playlistLocalRepository;
        this.userLocalRepository = toolBoxService.userLocalRepository;
        this.userService = userService;
        this.songService = songService;
    }

    public void createNewPlaylist (String playlistName, PlaylistEnum status, PlaylistServices playlistServices){
        playlistServices.adjustTemporaryPlaylistToNewPlaylist(playlistName, status);
        int playlistId = playlistServices.getPlaylistByName(playlistName).getPlaylistId();
        playlistServices.setCurrentPlaylistId(playlistId);
        printLN();
        printLNGreen("Playlist created successfully !");
    }

    public void createAllSongPlaylist (User user, PlaylistServices playlistServices){
        int allSongsPlaylistId = playlistServices.getAllSongsPlaylistId();
        List<Integer> playlists = user.getPlaylists();

        if (!playlists.contains(allSongsPlaylistId)) {
            playlists.addFirst(allSongsPlaylistId);
        }
        userLocalRepository.saveUser(user);
    }

    public void removePlaylistFromUser (int playlistId){
        User user = userLocalRepository.getUserById(userService.getCurrentUserId());
        List<Integer> actualPlaylists = user.getPlaylists();

        actualPlaylists.remove((Integer) playlistId);
        userService.saveUser(user);
    }

    public void deletePlaylist(int playlistId) {
        User user = userService.getUserById(userService.getCurrentUserId());
        Playlist playlist = playlistLocalRepository.getPlaylistById(playlistId);
        int playlistOwner = playlist.getOwnerId();

        if (playlistOwner == user.getUserId()){
            playlistLocalRepository.deletePlaylistById(playlistId);
            removePlaylistFromUser(playlistId);
            printLNGreen("Playlist deleted !");
        }
        else {
            if (playlist.getName().equals("AllSongs")){
                printLNInfo("You cannot delete the AllSongs playlist.");
            }
            removePlaylistFromUser(playlistId);
            printLNGreen("Playlist removed from your list.");
        }
    }

    public void renamePlayList(int playlistId, String newName) {
        Playlist playlist = playlistLocalRepository.getPlaylistById(playlistId);
        playlist.setName(newName);

        playlistLocalRepository.savePlaylist(playlist);
        printLNGreen("Playlist renamed to " + newName + " !");
    }

    public boolean verifyPlaylistName(String playlistName, User user) {
        List<Integer> userPlaylistsIds = user.getPlaylists();

        for (Integer playlistId : userPlaylistsIds) {
            Playlist playlist = playlistLocalRepository.getPlaylistById(playlistId);
            if (playlist != null && playlist.getName().equalsIgnoreCase(playlistName)) {
                return false;
            }
        }
        return true;
    }

    public void deleteSongFromPlaylist(int playlistId, int songIndex) {
        Playlist playlist = playlistLocalRepository.getPlaylistById(playlistId);

        int songId = songService.getSongById(playlist.getPlaylistSongsListWithId().get(songIndex)).getSongId();
        int currentSongId = songService.getCurrentSongId();

        playlist.getPlaylistSongsListWithId().remove(songIndex);
        playlistLocalRepository.savePlaylist(playlist);
    }

    public boolean isCurrentUserOwnerOfPlaylist(int playlistId) {
        Playlist playlist = playlistLocalRepository.getPlaylistById(playlistId);
        if (playlist == null) {
            return false;
        }
        int currentUserId = userService.getCurrentUserId();
        return playlist.getOwnerId() == currentUserId;
    }

    public int takeAndValidateInputSongChoice(int playlistId, PlaylistServices playlistServices) {
        Playlist playlist = playlistServices.getPlaylistById(playlistId);
        int chosenSong;

        while (true) {
            String input = this.scanner.nextLine();

            if (input.equals("0")) {
                return 0;
            }

            try {
                int inputNumber = Integer.parseInt(input);

                if (inputNumber < 1 || inputNumber > playlist.getPlaylistSongsListWithId().size()) {
                    printInfo("Invalid Playlist number.");
                    printLNInfo("Try again or press \"0\" to go back : ");
                } else {
                    chosenSong = inputNumber;
                    break;
                }
            } catch (NumberFormatException e) {
                printInfo("Invalid input, please enter a number : ");
            }
        }

        return chosenSong;
    }

    public int takeAndValidationInputPlaylistChoice() {
        User currentUser = userLocalRepository.getUserById(userService.getCurrentUserId());

        int chosenPlaylist;

        while (true) {
            String input = this.scanner.nextLine();

            if (input.equals("0")) {
                return 0;
            }

            try {
                int inputNumber = Integer.parseInt(input);

                if (inputNumber < 1 || inputNumber > currentUser.getPlaylists().size()) {
                    printInfo("Invalid Playlist number.");
                    printLNInfo("Try again or press \"0\" to go back : ");
                } else {
                    chosenPlaylist = currentUser.getPlaylists().get(inputNumber - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                printInfo("Invalid input, please enter a number : ");
            }
        }

        return chosenPlaylist;
    }

    public void playlistPageRouter(PlaylistServices playlistServices, PageService pageService) {
        int chosenPlaylist = takeAndValidationInputPlaylistChoice();

        if (chosenPlaylist == 0) {
            pageService.homePage.displayAllPage();
            return;
        }
        playlistServices.setCurrentPlaylistId(chosenPlaylist);
        songService.setCurrentSongId(playlistServices.getPlaylistById(chosenPlaylist).getPlaylistSongsListWithId().getFirst());

        if (isCurrentUserOwnerOfPlaylist(chosenPlaylist)){
            pageService.playlistPageOpen.displayAllPage();
        }
        else {
            pageService.playlistPageShared.displayAllPage();
        }
    }
}
