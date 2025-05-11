package clientSide.services;

import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import serverSide.entities.User;
import serverSide.repositories.PlaylistRepository;
import serverSide.repositories.UserRepository;
import java.util.List;
import java.util.Scanner;

public class PlaylistFunctionalitiesService {

    Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository;
    final PlaylistRepository playlistRepository;
    final TemporaryPlaylistService temporaryPlaylistService;
    private final UserService userService;

    public PlaylistFunctionalitiesService(PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
        this.userRepository = new UserRepository();
        this.userService = new UserService(userRepository);
        this.temporaryPlaylistService = new TemporaryPlaylistService(playlistRepository, userRepository);
    }

    public void createNewPlaylist (String playlistName, PlaylistEnum status, PlaylistServices playlistServices){
        playlistServices.createPlaylistWithTemporaryPlaylist(playlistName, status);
        int playlistId = playlistServices.getPlaylistByName(playlistName).getPlaylistId();
        playlistServices.setCurrentPlaylistId(playlistId);
        System.out.println();
        System.out.println("Playlist created successfully !");
    }

    public void createAllSongPlaylist (User user, PlaylistServices playlistServices){
        int allSongsPlaylistId = playlistServices.getAllSongsPlaylistId();
        List<Integer> playlists = user.getPlaylists();

        if (!playlists.contains(allSongsPlaylistId)) {
            playlists.addFirst(allSongsPlaylistId);
        }
        userRepository.saveUser(user);
    }

    public void removePlaylistFromUser (int playlistId){
        User user = userRepository.getUserById(userService.getCurrentUserId());
        List<Integer> actualPlaylists = user.getPlaylists();
        int playlistIndex = actualPlaylists.indexOf(playlistId);

        actualPlaylists.remove(playlistIndex);
    }

    public void deletePlaylist(int playlistId) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        if (playlist.getStatus().equals(PlaylistEnum.PRIVATE)){
            playlistRepository.deletePlaylistById(playlistId);
            System.out.println("Playlist deleted !");
        }
        else {
            if (playlist.getName().equals("AllSongs")){
                System.err.println("You cannot delete the AllSongs playlist.");
            }
            removePlaylistFromUser(playlistId);
            System.out.println("Playlist removed from your list.");
        }
    }

    public void renamePlayList(int playlistId, String newName) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        playlist.setName(newName);

        playlistRepository.savePlaylist(playlist);
        System.out.println("Playlist renamed to " + newName + " !");
    }

    public boolean verifyPlaylistName(String playlistName, User user) {
        List<Integer> userPlaylistsIds = user.getPlaylists();

        for (Integer playlistId : userPlaylistsIds) {
            Playlist playlist = playlistRepository.getPlaylistById(playlistId);
            if (playlist != null && playlist.getName().equalsIgnoreCase(playlistName)) {
                return false;
            }
        }
        return true;
    }

    public void deleteSongFromPlaylist(int playlistId, int songIndex) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        playlist.getPlaylistSongsListWithId().remove(songIndex);
        playlistRepository.savePlaylist(playlist);
    }

    public boolean isCurrentUserOwnerOfPlaylist(int playlistId) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
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

                if (inputNumber < 1 || inputNumber > playlist.getSize()) {
                    System.err.println("Invalid Playlist number.");
                    System.out.println("Try again or press \"0\" to go back : ");
                } else {
                    chosenSong = inputNumber-1;
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input, please enter a number : ");
            }
        }

        return chosenSong;
    }

    public int takeAndValidationInputPlaylistChoice() {
        User currentUser = userRepository.getUserById(userService.getCurrentUserId());

        int chosenPlaylist;

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

    public void playlistPageRouter(PageService pageService, SongService songService, PlaylistServices playlistServices) {
        int chosenPlaylist = takeAndValidationInputPlaylistChoice();

        if (chosenPlaylist == 0) {
            pageService.homePage.displayAllPage();
            return;
        }
        playlistServices.setCurrentPlaylistId(chosenPlaylist);
        songService.setCurrentSongId(playlistRepository.getPlaylistById(chosenPlaylist).getPlaylistSongsListWithId().getFirst());

        if (isCurrentUserOwnerOfPlaylist(chosenPlaylist)){
            pageService.playlistPageOpen.displayAllPage();
        }
        else {
            pageService.playlistPageShared.displayAllPage();
        }
    }
}
