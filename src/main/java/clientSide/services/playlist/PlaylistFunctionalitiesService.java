package clientSide.services.playlist;

import clientSide.services.PageService;
import clientSide.services.PlaylistServices;
import clientSide.services.SongService;
import clientSide.services.UserService;
import common.entities.Playlist;
import common.entities.PlaylistEnum;
import common.entities.User;
import common.repository.IPlaylistRepository;
import common.repository.IUserRepository;

import java.util.*;

import static clientSide.services.PrintHelper.*;

public class PlaylistFunctionalitiesService {

    private final UserService userService;
    private final SongService songService;

    public PlaylistFunctionalitiesService(UserService userService,
                                          SongService songService){
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

    public void createAllSongPlaylist (User user, PlaylistServices playlistServices, IUserRepository userRepository){
        int allSongsPlaylistId = playlistServices.getAllSongsPlaylistId();
        List<Integer> playlists = user.getPlaylists();

        if (!playlists.contains(allSongsPlaylistId)) {
            playlists.addFirst(allSongsPlaylistId);
        }
        userRepository.updateOrInsertUser(user);
    }

    public void removePlaylistFromUser (int playlistId, IUserRepository userRepository){
        User user = userRepository.getUserById(userService.getCurrentUserId());
        List<Integer> actualPlaylists = user.getPlaylists();

        actualPlaylists.remove((Integer) playlistId);
        userRepository.updateOrInsertUser(user);
    }

    public void deletePlaylist(int playlistId, IPlaylistRepository playlistRepository, IUserRepository userRepository) {
        User user = userService.getUserById(userService.getCurrentUserId());
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        int playlistOwner = playlist.getOwnerId();

        if (playlistOwner == user.getUserId()){
            playlistRepository.deletePlaylistById(playlistId);
            removePlaylistFromUser(playlistId, userRepository);
            printLNGreen("Playlist deleted !");
        }
        else {
            if (playlist.getName().equals("AllSongs")){
                printLNInfo("You cannot delete the AllSongs playlist.");
            }
            removePlaylistFromUser(playlistId, userRepository);
            printLNGreen("Playlist removed from your list.");
        }
    }

    public void renamePlayList(int playlistId, String newName, IPlaylistRepository playlistRepository) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        playlist.setName(newName);

        playlistRepository.updateOrInsertPlaylist(playlist);
        printLNGreen("Playlist renamed to " + newName + " !");
    }

    public boolean verifyPlaylistName(String playlistName, User user, IPlaylistRepository playlistRepository) {
        List<Integer> userPlaylistsIds = user.getPlaylists();

        for (Integer playlistId : userPlaylistsIds) {
            Playlist playlist = playlistRepository.getPlaylistById(playlistId);
            if (playlist != null && playlist.getName().equalsIgnoreCase(playlistName)) {
                return false;
            }
        }
        return true;
    }

    public void deleteSongFromPlaylist(int playlistId, int songIndex, IPlaylistRepository playlistRepository) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);

        playlist.getPlaylistSongsListWithId().remove(songIndex);
        playlistRepository.updateOrInsertPlaylist(playlist);
    }

    public boolean isCurrentUserOwnerOfPlaylist(int playlistId, IPlaylistRepository playlistRepository) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);
        if (playlist == null) {
            return false;
        }
        int currentUserId = userService.getCurrentUserId();
        return playlist.getOwnerId() == currentUserId;
    }

    public int takeAndValidateInputChoice(int totalSize, PageService pageService) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = pageService.gotAnInputGoBackIf0(scanner.nextLine());

            int inputNumber = pageService.tryParseInt(input);

            if (inputNumber < 1 || inputNumber > totalSize) {
                printInvalidInputTryAgainOrBack();
                printWhite("Your input : ");
            } else {
                choice = inputNumber;
                break;
            }
        }
        return choice;
    }

    public void playlistPageRouter(int totalSize, PlaylistServices playlistServices, PageService pageService, IPlaylistRepository playlistRepository) {

        int chosenPlaylist = takeAndValidateInputChoice(totalSize, pageService);
        int userId = userService.getCurrentUserId();
        int playlistId = userService.getUserById(userId).getPlaylists().get(chosenPlaylist-1);

        playlistServices.setCurrentPlaylistId(playlistId);
        songService.setCurrentSongId(playlistServices.getPlaylistById(playlistId).getPlaylistSongsListWithId().getFirst());

        if (isCurrentUserOwnerOfPlaylist(playlistId, playlistRepository)){
            pageService.playlistPageOpen.displayAllPage();
        }
        else {
            pageService.playlistPageShared.displayAllPage();
        }
    }
}
