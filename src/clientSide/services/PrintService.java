package clientSide.services;

import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import serverSide.entities.User;

import java.util.List;

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
        System.out.println("Songs found with information : " + info);
        printSongList (songs);
    }

    public void printSongList (List<Integer> songs){
        int i = 1;
        for (Integer song : songs) {
            System.out.println(i + ". " + songService.getSongById(song).getTitle()+ " - " +
                            artistService.getArtistNameBySong(song));
            i++;
        }
        System.out.println();
    }

    public void printPlaylist(List<Integer> playlistsId) {
        if (playlistsId == null || playlistsId.isEmpty()) {
            System.out.println("No playlist available.");
            return;
        }

        int i = 1;
        for (Integer playlistId : playlistsId) {
            Playlist playlist = playlistService.getPlaylistById(playlistId);
            if (playlist != null) {
                System.out.println(i + ". " + playlist.getName());
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
                    System.out.println(i + ". " +
                            playlist.getName() + " - " +
                            printPlaylistStatus(playlist.getStatus()) +
                            (isUserOwner ? icon.house() : ""));
                    i++;
                }
            }
        } else {
            System.out.println("No playlists available.");
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
                System.err.println("No friends actually.");
                return false;
            }
            for (int friendId : friendsId) {
                User friend = userService.getUserById(friendId);

                if (friend != null) {
                    System.out.println(i + ". " + friend.getPseudonym());
                    i++;
                }
            }
        }
        return true;
    }

    public void printUsers(List<Integer> usersId){
        if (usersId == null || usersId.isEmpty()) {
            System.out.println("No user found.");
            return;
        }
        int i = 1;
        for (int userId : usersId) {
            User user = userService.getUserById(userId);
            if (user != null) {
                System.out.println(i + ". " + user.getPseudonym());
                i++;
            }
        }
        System.out.println();
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
                    System.out.println(i + ". " +
                            playlist.getName() + " - " +
                            printPlaylistStatus(playlist.getStatus()) +
                            (isUserOwner ? icon.house() : ""));
                    i++;
                }
            }
        }
        else {
            System.out.println("No public playlists available.");
        }
    }
}
