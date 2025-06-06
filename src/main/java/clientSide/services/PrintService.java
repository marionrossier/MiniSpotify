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
            Song songData = songService.getSongById(song);
            printLNGrey(printSong(songData));
            i++;
        }
        printLN();
    }

    public String printSong (Song song){
        return "Song : " + song.getTitle()
                + " - " + artistService.getArtistNameBySong(song.getSongId())
                + ", album : " + song.getAlbum()
                + ", duration of " + getStringDuration(song.getDurationSeconds())
                + ", gender : " + song.getGender().getDisplayName();
    }

    public void printPlaylists(List<Integer> playlistsId) {
        if (playlistsId == null || playlistsId.isEmpty()) {
            printLNInfo("No playlist available.");
            return;
        }

        int i = 1;
        for (Integer playlistId : playlistsId) {
            Playlist playlist = playlistService.getPlaylistById(playlistId);
            if (playlist != null) {
                printPlaylist(playlist);
                i++;
            }
        }
        printLN();
    }

    public String printPlaylist (Playlist playlist){
        String owner = userService.getUserById(playlist.getOwnerId()).getPseudonym();
        return "Playlist : " + playlist.getName() +
                ", duration of " + getStringDuration(
                playlistService.setDurationSeconds(playlist.getPlaylistId())) +
                ", " + playlist.getPlaylistSongsListWithId().size() + " songs."
                + " || OWNER : " + owner
                + ", status : " + printPlaylistStatus(playlist.getStatus());
    }

    public void printUserPlaylists(int userId){
        int i = 1;
        User currentUser = userService.getUserById(userId);

        if (currentUser != null && currentUser.getPlaylists() != null) {
            for (int playlistId : userService.getUserById(userId).getPlaylists()) {
                Playlist playlist = playlistService.getPlaylistById(playlistId);

                if (playlist != null) {
                    printLNGrey(i + ". " + printPlaylist(playlist));
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
            return PrintHelper.PUBLIC;
        }
        return PrintHelper.PRIVATE;
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
        User user = userService.getUserById(friendId);
        if (user == null || user.getPlaylists() == null) {
            printLNInfo("No public playlists available.");
            return;
        }

        int i = 1;
        boolean hasPublicPlaylist = false;
        for (int playlistId : user.getPlaylists()) {
            Playlist playlist = playlistService.getPlaylistById(playlistId);
            if (playlist != null
                    && playlist.getStatus() == PlaylistEnum.PUBLIC
                    && playlist.getOwnerId() == user.getUserId()) {
                printLNGrey(i + ". " + printPlaylist(playlist));
                i++;
                hasPublicPlaylist = true;
            }
        }
        if (!hasPublicPlaylist) {
            printLNInfo("No public playlists available.");
        }
        printLN();
    }

    private String getPlaylistDuration(Playlist playlist) {
        int duration = 0;
        List<Integer> songIds = playlist.getPlaylistSongsListWithId();
        if (songIds != null) {
            for (int songId : songIds) {
                Song song = songService.getSongById(songId);
                if (song != null) {
                    duration += song.getDurationSeconds();
                }
            }
        }

        return getStringDuration(duration);
    }

    public String getStringDuration(int duration){
        int durationMinutes = duration / 60;
        int durationSeconds = duration % 60;
        int durationHours = durationMinutes / 60;
        durationMinutes = durationMinutes % 60;

        return String.format("%02d:%02d:%02d", durationHours, durationMinutes, durationSeconds);
    }
}
