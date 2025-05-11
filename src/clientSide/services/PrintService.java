package clientSide.services;

import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import serverSide.entities.User;
import serverSide.repositories.PlaylistRepository;
import serverSide.repositories.UserRepository;

import java.util.List;

public class PrintService {

    private final UserRepository userRepository = new UserRepository();
    private final PlaylistRepository playlistRepository = new PlaylistRepository();
    private final Icon icon = new Icon();

    public void printSongFound (List<Integer> songs, String info, SearchService searchService){
        System.out.println("Songs found with information : " + info);
        printSongList (songs, searchService);
    }

    public void printSongList (List<Integer> songs, SearchService searchService){
        int i = 1;
        for (Integer song : songs) {
            System.out.println(i + ". " + searchService.songRepository.getSongById(song).getTitleAndArtist());
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
        User currentUser = userRepository.getUserById(userId);

if (currentUser != null && currentUser.getPlaylists() != null) {
    for (int playlistId : userRepository.getUserById(userId).getPlaylists()) {
        Playlist playlist = playlistRepository.getPlaylistById(playlistId);

        if (playlist != null) {
            boolean isUserOwner = playlist.getOwnerId() == currentUser.getUserId();
            System.out.println(i + ". " +
                    playlist.getName() + " - " +
                    printPlaylistStatus(playlist.getStatus()) +
                    (isUserOwner ? icon.iconHouse() : ""));
            i++;
        }
    }
} else {
    System.out.println("No playlists available.");
}
    }

    private String printPlaylistStatus(PlaylistEnum status) {

        if (status == PlaylistEnum.PUBLIC){
            return icon.iconEarth();
        }
        return icon.iconLock();
    }
}
