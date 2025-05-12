package clientSide.services;

import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import serverSide.entities.Song;
import serverSide.entities.User;
import serverSide.repositoriesPattern.PlaylistLocalRepository;
import serverSide.repositoriesPattern.SongLocalRepository;

import java.util.*;

public class PlaylistServices {

    private final PlaylistLocalRepository playlistLocalRepository;
    private final TemporaryPlaylistService temporaryPlaylistService;
    private final PlaylistFunctionalitiesService playlistFuncService;
    private final SongLocalRepository songLocalRepository;


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
        Cookies_SingletonPattern.setCurrentPlaylistId(playlistId);
    }

    public int getCurrentPlaylistId (){
        return Cookies_SingletonPattern.getInstance().getCurrentPlaylistId();
    }

    public PlaylistEnum getPlaylistStatus (){
        return playlistLocalRepository.getPlaylistStatus(getCurrentPlaylistId());
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

    public List<Playlist> getPublicPlaylists() {
        List<Playlist> allPlaylists = playlistLocalRepository.getAllPlaylists();
        List<Playlist> publicPlaylist = new ArrayList<>();

        for (Playlist playlist : allPlaylists){
            if (playlist.getStatus().equals(PlaylistEnum.PUBLIC)){
                publicPlaylist.add(playlist);
            }
        }
        return  publicPlaylist;
    }

    public LinkedList<Playlist> getSharedPlaylist(User followedUser) {
        throw new UnsupportedOperationException("Not implemented yet");
        /*TODO*/
    }

    //PLAYLIST FUNCTIONALITIES :
    public void createNewPlaylist (String playlistName, PlaylistEnum status){
        playlistFuncService.createNewPlaylist(playlistName,status, this);
    }
    public void createAllSongPlaylist (User user){
        playlistFuncService.createAllSongPlaylist(user, this);
    }
    public void deletePlaylist(int playlistId) {
        playlistFuncService.deletePlaylist(playlistId, getCurrentPlaylistId());
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
    public void playlistPageRouter(PageService pageService, SongService songService) {
        playlistFuncService.playlistPageRouter(this, pageService);
    }

    //TEMPORARY PLAYLIST :
    public int getTemporaryPlaylistId() {
        return temporaryPlaylistService.getTemporaryPlaylistId();
    }
    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status) {
        temporaryPlaylistService.createTemporaryPlaylist(chosenSongs,status, this);
    }
    public void adjustTemporaryPlaylistToNewPlaylist(String playlistName, PlaylistEnum status) {
        temporaryPlaylistService.adjustTemporaryPlaylistToNewPlaylist(playlistName,status);
    }
    public void addSongToPlaylistFromTemporaryPlaylist(int temporaryPlaylistId, int finalPlaylistId) {
        temporaryPlaylistService.addSongToPlaylistFromTemporaryPlaylist(temporaryPlaylistId, finalPlaylistId);
    }
}
