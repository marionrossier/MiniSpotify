package clientSide.services;

import clientSide.entities.Playlist;
import clientSide.entities.PlaylistEnum;
import clientSide.repositories.PlaylistRepository;
import clientSide.repositories.UserRepository;

import java.util.LinkedList;

public class TemporaryPlaylistService {

    private final UserRepository userRepository;
    final PlaylistRepository playlistRepository;
    private final UserService userService;

    public TemporaryPlaylistService(PlaylistRepository playlistRepository, UserRepository userRepository){
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.userService = new UserService(userRepository);
    }

    public int getTemporaryPlaylistId() {
        Playlist playlist = playlistRepository.getPlaylistByName("temporaryPlaylist");
        if (playlist == null) {
            return new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE).getPlaylistId();
        }
        return playlist.getPlaylistId();
    }

    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status) {
        int currentUserId = userService.getCurrentUserId();
        Playlist temporaryPlaylist = playlistRepository.getTemporaryPlaylistOfCurrentUser(userService);

        if (temporaryPlaylist == null) {
            temporaryPlaylist = new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE);
            playlistRepository.savePlaylist(temporaryPlaylist);
        }
        temporaryPlaylist.setListSongsId(chosenSongs);

        int playlistDuration = temporaryPlaylist.getDurationSeconds();
        int playlistSize = temporaryPlaylist.getSize();

        temporaryPlaylist.setPlaylistInformation(playlistDuration, playlistSize);
        temporaryPlaylist.setOwnerId(currentUserId);
        temporaryPlaylist.setStatus(status);

        playlistRepository.savePlaylist(temporaryPlaylist);
    }

    public void createPlaylistWithTemporaryPlaylist(String playlistName, PlaylistEnum status) {

        Playlist newPlaylist = new Playlist(playlistName, PlaylistEnum.PRIVATE);

        Playlist temporaryPlaylist = playlistRepository.getPlaylistByName("temporaryPlaylist");

        newPlaylist.setListSongsId(temporaryPlaylist.getPlaylistSongsListWithId());
        int playlistDuration = newPlaylist.getDurationSeconds();
        int playlistSize = newPlaylist.getSize();
        newPlaylist.setPlaylistInformation(playlistDuration, playlistSize);
        newPlaylist.setOwnerId(userService.getCurrentUserId());
        newPlaylist.setStatus(status);

        playlistRepository.savePlaylist(newPlaylist);

        userService.addOnePlaylist(newPlaylist.getPlaylistId());

        this.deleteTemporaryPlaylist();
    }

    public void addSongToPlaylistFromTemporaryPlaylist(int temporaryPlaylistId, int finalPlaylistId) {
        Playlist temporaryPlaylist = playlistRepository.getPlaylistById(temporaryPlaylistId);
        Playlist targetPlaylist = playlistRepository.getPlaylistById(finalPlaylistId);

        if (targetPlaylist != null && temporaryPlaylist != null) {
            for (Integer songId : temporaryPlaylist.getPlaylistSongsListWithId()) {
                if (!targetPlaylist.getPlaylistSongsListWithId().contains(songId)) {
                    targetPlaylist.getPlaylistSongsListWithId().add(songId);
                }
            }
            playlistRepository.savePlaylist(targetPlaylist);
        } else {
            System.err.println("Target playlist or temporary playlist not found.");
        }
    }

    public void deleteTemporaryPlaylist() {
        Playlist temporaryPlaylist = playlistRepository.getPlaylistById(getTemporaryPlaylistId());
        if (temporaryPlaylist != null) {
            int playlistId = temporaryPlaylist.getPlaylistId();
            playlistRepository.deletePlaylistById(playlistId);
        }
    }
}
