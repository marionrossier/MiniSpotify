package clientSide.services;

import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import middle.IPlaylistRepository;

import java.util.LinkedList;

public class TemporaryPlaylistService {

    private final IPlaylistRepository playlistLocalRepository;
    private final UserService userService;

    public TemporaryPlaylistService(ToolBoxService toolBoxService, UserService userService){
        this.playlistLocalRepository = toolBoxService.playlistLocalRepository;
        this.userService = userService;
    }

    public int getTemporaryPlaylistId() {
        Playlist playlist = playlistLocalRepository.getPlaylistByName("temporaryPlaylist");
        if (playlist == null) {
            return new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE).getPlaylistId();
        }
        return playlist.getPlaylistId();
    }

    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status,
                                        PlaylistServices playlistServices) {
        if (chosenSongs==null){
            return;
        }
        int currentUserId = userService.getCurrentUserId();
        Playlist temporaryPlaylist = playlistLocalRepository.getTemporaryPlaylistOfCurrentUser(currentUserId);

        if (temporaryPlaylist == null) {
            temporaryPlaylist = new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE);
            playlistLocalRepository.savePlaylist(temporaryPlaylist);
        }
        temporaryPlaylist.setListSongsId(chosenSongs);

        int playlistDuration = playlistServices.setDurationSeconds(temporaryPlaylist.getPlaylistId());
        int playlistSize = temporaryPlaylist.getSize();

        temporaryPlaylist.setPlaylistInformation(playlistDuration, playlistSize);
        temporaryPlaylist.setOwnerId(currentUserId);
        temporaryPlaylist.setStatus(status);

        playlistLocalRepository.savePlaylist(temporaryPlaylist);
    }

    public void adjustTemporaryPlaylistToNewPlaylist(String playlistName, PlaylistEnum status) {
        int currentUserId = userService.getCurrentUserId();
        Playlist newPlaylist = playlistLocalRepository.getTemporaryPlaylistOfCurrentUser(currentUserId);
        if (newPlaylist != null) {
            newPlaylist.setName(playlistName);
            newPlaylist.setStatus(status);
            playlistLocalRepository.savePlaylist(newPlaylist);
            userService.addOnePlaylist(newPlaylist.getPlaylistId());
        } else {
            System.err.println("Temporary playlist not found.");
        }
    }

    public void addSongToPlaylistFromTemporaryPlaylist(int temporaryPlaylistId, int finalPlaylistId) {
        Playlist temporaryPlaylist = playlistLocalRepository.getPlaylistById(temporaryPlaylistId);
        Playlist targetPlaylist = playlistLocalRepository.getPlaylistById(finalPlaylistId);

        if (targetPlaylist != null && temporaryPlaylist != null) {
            for (Integer songId : temporaryPlaylist.getPlaylistSongsListWithId()) {
                if (!targetPlaylist.getPlaylistSongsListWithId().contains(songId)) {
                    targetPlaylist.getPlaylistSongsListWithId().add(songId);
                }
            }
            playlistLocalRepository.savePlaylist(targetPlaylist);
        } else {
            System.err.println("Target playlist or temporary playlist not found.");
        }
    }
}
