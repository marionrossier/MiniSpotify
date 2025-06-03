package clientSide.services;

import common.entities.*;

import java.util.List;

import static clientSide.services.PrintHelper.*;

public class PrintService {

    private final UserService userService;
    private final PlaylistServices playlistService;
    private final SongService songService;
    private final ArtistService artistService;

    public PrintService(SongService songService,
                        ArtistService artistService,
                        PlaylistServices playlistServices,
                        UserService userService) {
        this.artistService = artistService;
        this.songService = songService;
        this.userService = userService;
        this.playlistService = playlistServices;
    }

    public void printSongList (List<Integer> songs){
        int i = 1;
        for (Integer song : songs) {
            printLNGrey(i + ". " + songService.getSongById(song).getTitle()+ " - " +
                            artistService.getArtistNameBySong(song));
            i++;
        }
        printLN();
    }

    public void printPlaylist(List<Integer> playlistsId) {
        if (playlistsId == null || playlistsId.isEmpty()) {
            printLNInfo("No playlist available.");
            return;
        }

        int i = 1;
        for (Integer playlistId : playlistsId) {
            Playlist playlist = playlistService.getPlaylistById(playlistId);
            if (playlist != null) {
                printLNGrey(i + ". " + playlist.getName());
                i++;
            }
        }
        printLN();
    }

    public void printUserPlaylists(int userId){
        int i = 1;
        User currentUser = userService.getUserById(userId);

        if (currentUser != null && currentUser.getPlaylists() != null) {
            for (int playlistId : userService.getUserById(userId).getPlaylists()) {
                Playlist playlist = playlistService.getPlaylistById(playlistId);

                if (playlist != null) {
                    boolean isUserOwner = playlist.getOwnerId() == currentUser.getUserId();
                    printLNGrey(i + ". " +
                            playlist.getName() + " - " +
                            printPlaylistStatus(playlist.getStatus()) +
                            (isUserOwner ? PrintHelper.house : ""));
                    i++;
                }
            }
        } else {
            printLNInfo("No playlists available.");
        }
        printLN();
    }

    private String printPlaylistStatus(PlaylistEnum status) {

        if (status == PlaylistEnum.PUBLIC){
            return PrintHelper.earth;
        }
        return PrintHelper.lock;
    }

    public boolean printUserFriends(int userId){
        int i = 1;
        User user = userService.getUserById(userId);

        List<Integer> friendsId = userService.getUserById(userId).getFriends();
        if (user != null) {
            if (friendsId.isEmpty()){
                printLNInfo("No friends actually.");
                return false;
            }
            for (int friendId : friendsId) {
                User friend = userService.getUserById(friendId);

                if (friend != null) {
                    printLNGrey(i + ". " + friend.getPseudonym());
                    i++;
                }
            }
            printLN();
        }
        return true;
    }

    public void printUsers(List<Integer> usersId){
        if (usersId == null || usersId.isEmpty()) {
            printLNInfo("No user found.");
            return;
        }
        int i = 1;
        for (int userId : usersId) {
            User user = userService.getUserById(userId);
            if (user != null) {
                printLNGrey(i + ". " + user.getPseudonym());
                i++;
            }
        }
        printLN();
    }

    public void printUserPublicPlaylists(int friendId) {
        int i = 1;
        User user = userService.getUserById(friendId);

        if (user != null && user.getPlaylists() != null) {
            for (int playlistId : userService.getUserById(friendId).getPlaylists()) {
                Playlist playlist = playlistService.getPlaylistById(playlistId);

                if ((playlist != null)
                        && (playlist.getStatus().equals(PlaylistEnum.PUBLIC))
                        && (playlist.getOwnerId() == user.getUserId())) {
                    boolean isUserOwner = playlist.getOwnerId() == user.getUserId();
                    printLNGrey(i + ". " +
                            playlist.getName() + " - " +
                            printPlaylistStatus(playlist.getStatus()) +
                            (isUserOwner ? PrintHelper.house : ""));
                    i++;
                }
                printLN();
            }
        }
        else {
            printLNInfo("No public playlists available.");
        }
    }
}
