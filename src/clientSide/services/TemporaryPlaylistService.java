package clientSide.services;

import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import serverSide.repositories.PlaylistLocalRepository;

import java.util.LinkedList;

public class TemporaryPlaylistService {

    private final PlaylistLocalRepository playlistLocalRepository;
    private final UserService userService;

    public TemporaryPlaylistService(ServiceToolBox serviceToolBox, UserService userService){
        this.playlistLocalRepository = serviceToolBox.playlistLocalRepository;
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
        int currentUserId = userService.getCurrentUserId();
        Playlist temporaryPlaylist = playlistLocalRepository.getTemporaryPlaylistOfCurrentUser(userService);

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

    public void createPlaylistWithTemporaryPlaylist(String playlistName, PlaylistEnum status,
                                                    PlaylistServices playlistServices) {

        Playlist newPlaylist = new Playlist(playlistName, PlaylistEnum.PRIVATE);

        Playlist temporaryPlaylist = playlistLocalRepository.getTemporaryPlaylistOfCurrentUser(userService);

        newPlaylist.setListSongsId(temporaryPlaylist.getPlaylistSongsListWithId());
        int playlistDuration = playlistServices.setDurationSeconds(newPlaylist.getPlaylistId());
        int playlistSize = newPlaylist.getSize();
        newPlaylist.setPlaylistInformation(playlistDuration, playlistSize);
        newPlaylist.setOwnerId(userService.getCurrentUserId());
        newPlaylist.setStatus(status);

        playlistLocalRepository.savePlaylist(newPlaylist);

        userService.addOnePlaylist(newPlaylist.getPlaylistId());

        this.deleteTemporaryPlaylist();
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

    public void deleteTemporaryPlaylist() {
        Playlist temporaryPlaylist = playlistLocalRepository.getPlaylistById(getTemporaryPlaylistId());
        if (temporaryPlaylist != null) {
            int playlistId = temporaryPlaylist.getPlaylistId();
            playlistLocalRepository.deletePlaylistById(playlistId);
        }
    }
}
