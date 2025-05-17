package clientSide.services;

import serverSide.entities.*;

import java.util.List;

import static clientSide.services.PrintHelper.*;

public class PrintService {

    private final IconService icon = new IconService();
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

    public void printSongFound (List<Integer> songs, String info){
        printLNWhite("Songs found with information : " + info);
        printSongList (songs);
    }

    public void printSongList (List<Integer> songs){
        int i = 1;
        for (Integer song : songs) {
            printLNWhite(i + ". " + songService.getSongById(song).getTitle()+ " - " +
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
                printLNWhite(i + ". " + playlist.getName());
                i++;
            }
        }
    }

    public void printUserPlaylists(int userId){
        int i = 1;
        User currentUser = userService.getUserById(userId);

        if (currentUser != null && currentUser.getPlaylists() != null) {
            for (int playlistId : userService.getUserById(userId).getPlaylists()) {
                Playlist playlist = playlistService.getPlaylistById(playlistId);

                if (playlist != null) {
                    boolean isUserOwner = playlist.getOwnerId() == currentUser.getUserId();
                    printLNWhite(i + ". " +
                            playlist.getName() + " - " +
                            printPlaylistStatus(playlist.getStatus()) +
                            (isUserOwner ? icon.house() : ""));
                    i++;
                }
            }
        } else {
            printLNInfo("No playlists available.");
        }
    }

    private String printPlaylistStatus(PlaylistEnum status) {

        if (status == PlaylistEnum.PUBLIC){
            return icon.earth();
        }
        return icon.lock();
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
                    printLNWhite(i + ". " + friend.getPseudonym());
                    i++;
                }
            }
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
                printLNWhite(i + ". " + user.getPseudonym());
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
                    printLNWhite(i + ". " +
                            playlist.getName() + " - " +
                            printPlaylistStatus(playlist.getStatus()) +
                            (isUserOwner ? icon.house() : ""));
                    i++;
                }
            }
        }
        else {
            printLNInfo("No public playlists available.");
        }
    }
}
