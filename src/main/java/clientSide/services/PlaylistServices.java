package clientSide.services;

import clientSide.services.playlist.*;
import common.entities.*;
import common.repository.*;

import java.util.*;

import static clientSide.services.PrintHelper.*;

public class PlaylistServices {

    private final IPlaylistRepository playlistRepository;
    private final TemporaryPlaylistService temporaryPlaylistService;
    private final PlaylistFunctionalitiesService playlistFuncService;
    private final PlaylistReorderSongService playlistReorderSongService;
    private final ISongRepository songRepository;
    private final IUserRepository userRepository;


    public PlaylistServices (ToolBoxService toolBoxService,
                             PlaylistFunctionalitiesService playlistFuncService,
                             TemporaryPlaylistService temporaryPlaylistService,
                             PlaylistReorderSongService playlistReorderSongService){
        this.playlistRepository = toolBoxService.playlistRepository;
        this.temporaryPlaylistService = temporaryPlaylistService;
        this.playlistFuncService = playlistFuncService;
        this.songRepository = toolBoxService.songRepository;
        this.playlistReorderSongService = playlistReorderSongService;
        this.userRepository = toolBoxService.userRepository;
    }

    public int setDurationSeconds(int playlistId) {
        int totalSeconds = 0;
        Playlist playlist = this.playlistRepository.getPlaylistById(playlistId);
        if (playlist != null) {
            for (Integer songId : playlist.getPlaylistSongsListWithId()) {
                Song song = songRepository.getSongById(songId);
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
        return playlistRepository.getPlaylistStatus(playlist);
    }

    public int getAllSongsPlaylistId (){
        return playlistRepository.getPlaylistByName("AllSongs").getPlaylistId();
    }

    public Playlist getPlaylistByName(String name) {
        return playlistRepository.getPlaylistByName(name);
    }

    public Playlist getPlaylistById(int id) {
        return playlistRepository.getPlaylistById(id);
    }

    public List<Integer> getPublicPlaylists(int currentUserId) {
        User user = userRepository.getUserById(currentUserId);

        List<Integer> friendIds = user.getFriends();
        friendIds.add(1);

        List<Integer> publicPlaylistIds = new ArrayList<>();
        for (Integer friendId : friendIds) {
            User friend = userRepository.getUserById(friendId);
            if (friend != null) {
                for (Integer playlistId : friend.getPlaylists()) {
                    Playlist playlist = playlistRepository.getPlaylistById(playlistId);
                    if (playlist != null && playlist.getStatus() == PlaylistEnum.PUBLIC) {
                        publicPlaylistIds.add(playlistId);
                    }
                }
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
        playlistFuncService.createAllSongPlaylist(user, this, userRepository);
    }
    public void deletePlaylist(int playlistId) {
        playlistFuncService.deletePlaylist(playlistId, playlistRepository, userRepository);
    }
    public void renamePlayList(int playlistId, String newName) {
        playlistFuncService.renamePlayList(playlistId, newName, playlistRepository);
    }
    public boolean verifyPlaylistName(String playlistName, User user) {
        return playlistFuncService.verifyPlaylistName(playlistName, user, playlistRepository);
    }
    public void deleteSongFromPlaylist(int playlistId, int songIndex) {
       playlistFuncService.deleteSongFromPlaylist(playlistId, songIndex, playlistRepository);
    }
    public boolean isCurrentUserOwnerOfPlaylist(int playlistId) {
        return playlistFuncService.isCurrentUserOwnerOfPlaylist(playlistId, playlistRepository);
    }
    public int takeAndValidateInputChoice(int totalSize, PageService pageService) {
       return playlistFuncService.takeAndValidateInputChoice(totalSize, pageService);
    }
    public void playlistPageRouter(int totalSize, PageService pageService) {
        playlistFuncService.playlistPageRouter(totalSize, this, pageService, playlistRepository);
    }

    //TEMPORARY PLAYLIST :
    public int getTemporaryPlaylistId() {
        return temporaryPlaylistService.getTemporaryPlaylistId(playlistRepository);
    }
    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status) {
        temporaryPlaylistService.createTemporaryPlaylist(chosenSongs,status, playlistRepository);
    }
    public void adjustTemporaryPlaylistToNewPlaylist(String playlistName, PlaylistEnum status) {
        temporaryPlaylistService.adjustTemporaryPlaylistToNewPlaylist(playlistName,status, playlistRepository);
    }
    public void addSongToPlaylistFromTemporaryPlaylist(int temporaryPlaylistId, int finalPlaylistId) {
        temporaryPlaylistService.addSongToPlaylistFromTemporaryPlaylist(temporaryPlaylistId, finalPlaylistId, playlistRepository);
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

    //PLAYLIST REORDER SONG :
    public void reorderSongsInPlaylist(int playlistId, Scanner scanner) {
        playlistReorderSongService.reorderSongsInPlaylist(playlistId,this, scanner, playlistRepository);
    }
}
