package clientSide.services;

import clientSide.entities.Playlist;
import clientSide.entities.PlaylistEnum;
import clientSide.entities.Song;
import clientSide.entities.User;
import clientSide.repositories.PlaylistRepository;
import clientSide.repositories.SongRepository;
import clientSide.repositories.UserRepository;

import java.util.*;

public class PlaylistServices {

    public final UserRepository userRepository;
    public final PlaylistRepository playlistRepository;
    public final TemporaryPlaylistService temporaryPlaylistService;
    public final PlaylistFunctionalitiesService playlistFuncService;
    public final SongRepository songRepository;

    public PlaylistServices (PlaylistRepository playlistRepository, UserRepository userRepository, SongRepository songRepository){
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.temporaryPlaylistService = new TemporaryPlaylistService(playlistRepository, userRepository);
        this.playlistFuncService = new PlaylistFunctionalitiesService(playlistRepository);
        this.songRepository = songRepository;
    }

    public PlaylistServices (PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = new UserRepository();
        this.temporaryPlaylistService = new TemporaryPlaylistService(playlistRepository, userRepository);
        this.playlistFuncService = new PlaylistFunctionalitiesService(playlistRepository);
        this.songRepository = songRepository;
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
        Cookies_SingletonPattern.setCurrentPlaylistId(playlistId);
    }

    public int getCurrentPlaylistId (){
        return Cookies_SingletonPattern.getInstance().getCurrentPlaylistId();
    }

    public PlaylistEnum getPlaylistStatus (){
        return playlistRepository.getPlaylistStatus(getCurrentPlaylistId());
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

    public List<Playlist> getPublicPlaylists() {
        List<Playlist> allPlaylists = playlistRepository.getAllPlaylists();
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
    public void playlistPageRouter(PageService pageService, SongService songService) {
        playlistFuncService.playlistPageRouter(pageService, songService, this);
    }

    //TEMPORARY PLAYLIST :
    public int getTemporaryPlaylistId() {
        return temporaryPlaylistService.getTemporaryPlaylistId();
    }
    public void createTemporaryPlaylist(LinkedList<Integer> chosenSongs, PlaylistEnum status) {
        temporaryPlaylistService.createTemporaryPlaylist(chosenSongs,status, this);
    }
    public void createPlaylistWithTemporaryPlaylist(String playlistName, PlaylistEnum status) {
        temporaryPlaylistService.createPlaylistWithTemporaryPlaylist(playlistName,status, this);
    }
    public void addSongToPlaylistFromTemporaryPlaylist(int temporaryPlaylistId, int finalPlaylistId) {
        temporaryPlaylistService.addSongToPlaylistFromTemporaryPlaylist(temporaryPlaylistId, finalPlaylistId);
    }
    public void deleteTemporaryPlaylist() {
        temporaryPlaylistService.deleteTemporaryPlaylist();
    }
}
