package services;

import data.entities.MusicGender;
import data.entities.Playlist;
import data.entities.PlaylistEnum;
import data.entities.User;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import data.jsons.UserRepository;

import java.util.List;

public class PrintService {

    private final SongRepository songRepository = new SongRepository();
    private final UserRepository userRepository = new UserRepository();
    private final PlaylistRepository playlistRepository = new PlaylistRepository();
    private final Icon icon = new Icon();

    public void printSongFound (List<Integer> songs, String info){
        System.out.println("Songs found with information : " + info);
        printSongList (songs);
    }

    public void printSongList (List<Integer> songs){
        int i = 1;
        for (Integer song : songs) {
            System.out.println(i + ". " + songRepository.getSongById(song).getTitleAndArtist());
            i++;
        }
        System.out.println();
    }

    public void printUserPlaylists(int userId){
        int i = 1;
        User currentUser = userRepository.getUserById(userId);

        if (currentUser != null && currentUser.getPlaylists() != null){
            for (int playlistId : userRepository.getUserById(userId).getPlaylists()) {
                Playlist playlist = playlistRepository.getPlaylistById(playlistId);
                if (playlist != null) {
                    System.out.println(i + ". " + playlist.getName() + " - " + printPlaylistStatus(playlist.getStatus()));
                    i++;
                }
            }
        }
        else {
            System.out.println("No playlists available.");
        }
    }

    private String printPlaylistStatus(PlaylistEnum status) {
        if (status == PlaylistEnum.PUBLIC){
            return icon.iconEarth();
        }
        return icon.iconLock();
    }

    public void printGenderList() {
        int i = 1;
        for (MusicGender gender : MusicGender.values()) {
            System.out.println(i + ". " + gender.getDisplayName());
            i++;
        }
    }
}
