package clientSide.services.playlist;

import clientSide.services.UserService;
import common.entities.Playlist;
import common.entities.PlaylistEnum;
import common.repository.IPlaylistRepository;

import java.util.LinkedList;

import static clientSide.services.PrintHelper.*;

public class TemporaryPlaylistService {

    private final UserService userService;

    public TemporaryPlaylistService(UserService userService){
        this.userService = userService;
    }

    public int getTemporaryPlaylistId(IPlaylistRepository playlistRepository) {
        Playlist playlist = playlistRepository.getPlaylistByName("temporaryPlaylist");
        if (playlist == null) {
            return new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE).getPlaylistId();
        }
        return playlist.getPlaylistId();
    }

    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status, IPlaylistRepository playlistRepository) {
        if (chosenSongs==null){
            return;
        }
        int currentUserId = userService.getCurrentUserId();
        Playlist temporaryPlaylist = playlistRepository.getTemporaryPlaylistOfCurrentUser(currentUserId);

        if (temporaryPlaylist == null) {
            temporaryPlaylist = new Playlist("temporaryPlaylist", PlaylistEnum.PRIVATE);
            playlistRepository.updateOrInsertPlaylist(temporaryPlaylist);
        }
        temporaryPlaylist.setListSongsId(chosenSongs);

        temporaryPlaylist.setOwnerId(currentUserId);
        temporaryPlaylist.setStatus(status);

        playlistRepository.updateOrInsertPlaylist(temporaryPlaylist);
    }

    public void adjustTemporaryPlaylistToNewPlaylist(String playlistName, PlaylistEnum status, IPlaylistRepository playlistRepository) {
        int currentUserId = userService.getCurrentUserId();
        Playlist newPlaylist = playlistRepository.getTemporaryPlaylistOfCurrentUser(currentUserId);
        if (newPlaylist != null) {
            newPlaylist.setName(playlistName);
            newPlaylist.setStatus(status);
            playlistRepository.updateOrInsertPlaylist(newPlaylist);
            userService.addOnePlaylistToCurrentUser(newPlaylist.getPlaylistId());
        } else {
            printLNError("Temporary playlist not found.");
        }
    }

    public void addSongToPlaylistFromTemporaryPlaylist(int temporaryPlaylistId, int finalPlaylistId, IPlaylistRepository playlistRepository) {
        Playlist temporaryPlaylist = playlistRepository.getPlaylistById(temporaryPlaylistId);
        Playlist targetPlaylist = playlistRepository.getPlaylistById(finalPlaylistId);

        if (targetPlaylist != null && temporaryPlaylist != null) {
            for (Integer songId : temporaryPlaylist.getPlaylistSongsListWithId()) {
                if (!targetPlaylist.getPlaylistSongsListWithId().contains(songId)) {
                    targetPlaylist.getPlaylistSongsListWithId().add(songId);
                }
            }
            playlistRepository.updateOrInsertPlaylist(targetPlaylist);
        } else {
            printLNError("Target playlist or temporary playlist not found.");
        }
    }
}
