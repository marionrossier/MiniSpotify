package clientSide.services;

import common.entities.Playlist;
import common.entities.PlaylistEnum;
import common.entities.Song;
import common.entities.User;
import common.*;

import java.util.*;

import static clientSide.services.PrintHelper.*;

public class PlaylistServices {

    private final IPlaylistRepository playlistLocalRepository;
    private final TemporaryPlaylistService temporaryPlaylistService;
    private final PlaylistFunctionalitiesService playlistFuncService;
    private final ISongRepository songLocalRepository;


    public PlaylistServices (ToolBoxService toolBoxService,
                             PlaylistFunctionalitiesService playlistFuncService,
                             TemporaryPlaylistService temporaryPlaylistService){
        this.playlistLocalRepository = toolBoxService.playlistLocalRepository;
        this.temporaryPlaylistService = temporaryPlaylistService;
        this.playlistFuncService = playlistFuncService;
        this.songLocalRepository = toolBoxService.songLocalRepository;
    }

    public int setDurationSeconds(int playlistId) {
        int totalSeconds = 0;
        Playlist playlist = this.playlistLocalRepository.getPlaylistById(playlistId);
        if (playlist != null) {
            for (Integer songId : playlist.getPlaylistSongsListWithId()) {
                Song song = songLocalRepository.getSongById(songId);
                if (song != null) {
                    totalSeconds += song.getDurationSeconds();
                }
            }
        }
        return totalSeconds;
    }

    public void setCurrentPlaylistId (int playlistId){
        Cookies.setCurrentPlaylistId(playlistId);
    }

    public int getCurrentPlaylistId (){
        return Cookies.getInstance().getCurrentPlaylistId();
    }

    public PlaylistEnum getPlaylistStatus (){
        Playlist playlist = getPlaylistById(getCurrentPlaylistId());
        return playlistLocalRepository.getPlaylistStatus(playlist);
    }

    public int getAllSongsPlaylistId (){
        return playlistLocalRepository.getPlaylistByName("AllSongs").getPlaylistId();
    }

    public Playlist getPlaylistByName(String name) {
        return playlistLocalRepository.getPlaylistByName(name);
    }

    public Playlist getPlaylistById(int id) {
        return playlistLocalRepository.getPlaylistById(id);
    }

    public List<Integer> getPublicPlaylists() {
        List<Playlist> allPlaylists = playlistLocalRepository.getAllPlaylists();
        List<Integer> publicPlaylistIds = new ArrayList<>();

        for (Playlist playlist : allPlaylists){
            if (playlist.getStatus().equals(PlaylistEnum.PUBLIC)){
                publicPlaylistIds.add(playlist.getPlaylistId());
            }
        }
        return publicPlaylistIds;
    }

    public List<Integer> getUserPublicPlaylists(User user){
        List<Integer> playlists = user.getPlaylists();
        List<Integer> finalList = new ArrayList<>();
        for (int playlistId : playlists){
            Playlist playlist = getPlaylistById(playlistId);
            if (playlist.getStatus() == PlaylistEnum.PUBLIC){
                finalList.add(playlistId);
            }
        }
        return finalList;
    }

    public int getCurrentFriendPlaylistId (){
        return Cookies.getInstance().getCurrentFriendPlaylistId();
    }

    public void setCurrentFriendPlaylistId (int playlistId){
        Cookies.getInstance().setCurrentFriendPlaylistId(playlistId);
    }

    //PLAYLIST FUNCTIONALITIES :
    public void createNewPlaylist (String playlistName, PlaylistEnum status){
        playlistFuncService.createNewPlaylist(playlistName,status, this);
    }
    public void createAllSongPlaylist (User user){
        playlistFuncService.createAllSongPlaylist(user, this);
    }
    public void deletePlaylist(int playlistId) {
        playlistFuncService.deletePlaylist(playlistId);
    }
    public void renamePlayList(int playlistId, String newName) {
        playlistFuncService.renamePlayList(playlistId, newName);
    }
    public boolean verifyPlaylistName(String playlistName, User user) {
        return playlistFuncService.verifyPlaylistName(playlistName, user);
    }
    public void deleteSongFromPlaylist(int playlistId, int songIndex) {
       playlistFuncService.deleteSongFromPlaylist(playlistId, songIndex);
    }
    public boolean isCurrentUserOwnerOfPlaylist(int playlistId) {
        return playlistFuncService.isCurrentUserOwnerOfPlaylist(playlistId);
    }
    public int takeAndValidateInputSongChoice(int playlistId) {
       return playlistFuncService.takeAndValidateInputSongChoice(playlistId, this);
    }
    public int takeAndValidationInputPlaylistChoice() {
        return playlistFuncService.takeAndValidationInputPlaylistChoice();
    }
    public void playlistPageRouter(PageService pageService) {
        playlistFuncService.playlistPageRouter(this, pageService);
    }

    //TEMPORARY PLAYLIST :
    public int getTemporaryPlaylistId() {
        return temporaryPlaylistService.getTemporaryPlaylistId();
    }
    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status) {
        temporaryPlaylistService.createTemporaryPlaylist(chosenSongs,status);
    }
    public void adjustTemporaryPlaylistToNewPlaylist(String playlistName, PlaylistEnum status) {
        temporaryPlaylistService.adjustTemporaryPlaylistToNewPlaylist(playlistName,status);
    }
    public void addSongToPlaylistFromTemporaryPlaylist(int temporaryPlaylistId, int finalPlaylistId) {
        temporaryPlaylistService.addSongToPlaylistFromTemporaryPlaylist(temporaryPlaylistId, finalPlaylistId);
    }

    public void getAndAddSelectionOfPlaylistsToCurrentUserPlaylists(List<Integer> playlist,
                                                                    LinkedList<Integer> chosenPlaylists,
                                                                    ToolBoxView toolBoxView) {

        for (Integer playlistIndex : chosenPlaylists) {
            int playlistId = playlist.get(playlistIndex);
            if (!toolBoxView.getUserServ().getUserById(toolBoxView.getUserServ().getCurrentUserId())
                    .getPlaylists()
                    .contains(playlistId)) {
                toolBoxView.getUserServ().addOnePlaylistToCurrentUser(playlistId);
            }
        }
        printLNGreen("Playlist.s has been added.");
    }
}
