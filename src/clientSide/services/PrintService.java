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

    public void printPlaylist(List<Playlist> playlists) {
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("No playlist available.");
            return;
        }

        int i = 1;
        for (Playlist playlist : playlists) {
            System.out.println(i + ". " + playlist.getName());
            i++;
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
}
