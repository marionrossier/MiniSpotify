package clientSide.services;

import common.entities.Playlist;
import common.entities.PlaylistEnum;
import common.*;

import java.util.LinkedList;

import static clientSide.services.PrintHelper.*;

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

    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status) {
        if (chosenSongs==null){
            return;
        }
        int currentUserId = userService.getCurrentUserId();
        Playlist temporaryPlaylist = playlistLocalRepository.getTemporaryPlaylistOfCurrentUser(currentUserId);

        if (temporaryPlaylist == null) {
            temporaryPlaylist = new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE);
            playlistLocalRepository.updateOrInsertPlaylist(temporaryPlaylist);
        }
        temporaryPlaylist.setListSongsId(chosenSongs);

        temporaryPlaylist.setOwnerId(currentUserId);
        temporaryPlaylist.setStatus(status);

        playlistLocalRepository.updateOrInsertPlaylist(temporaryPlaylist);
    }

    public void adjustTemporaryPlaylistToNewPlaylist(String playlistName, PlaylistEnum status) {
        int currentUserId = userService.getCurrentUserId();
        Playlist newPlaylist = playlistLocalRepository.getTemporaryPlaylistOfCurrentUser(currentUserId);
        if (newPlaylist != null) {
            newPlaylist.setName(playlistName);
            newPlaylist.setStatus(status);
            playlistLocalRepository.updateOrInsertPlaylist(newPlaylist);
            userService.addOnePlaylistToCurrentUser(newPlaylist.getPlaylistId());
        } else {
            printLNError("Temporary playlist not found.");
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
            playlistLocalRepository.updateOrInsertPlaylist(targetPlaylist);
        } else {
            printLNError("Target playlist or temporary playlist not found.");
        }
    }
}
