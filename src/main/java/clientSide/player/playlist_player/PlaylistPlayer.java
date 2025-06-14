package clientSide.player.playlist_player;

import clientSide.services.ArtistService;
import clientSide.services.PlaylistServices;
import clientSide.services.PrintService;
import clientSide.services.SongService;
import common.entities.Playlist;
import common.entities.Song;
import clientSide.player.file_player.*;

import java.util.*;

import static clientSide.services.PrintHelper.*;

public class PlaylistPlayer implements IPlaylistPlayer {

    private final IMusicPlayer musicPlayer;
    protected PlaylistServices playlistServices;
    protected ArtistService artistService;
    protected PrintService printService;
    protected SongService songService;

    protected LinkedList<Integer> songIdHistory = new LinkedList<>();
    int currentHistoryIndex = -1;

    protected Song currentSong;
    protected Playlist currentPlaylist;
    private int lastPlayedPlaylistId = -1;


    //STATE PATTERN
    private IState currentState;
    private final IState sequentialState;
    private final IState shuffleState;
    private final IState repeatState;

    public PlaylistPlayer(IMusicPlayer musicPlayer, SongService songService,
                          PlaylistServices playlistServices, ArtistService artistService,
                          PrintService printService) {
        this.musicPlayer = musicPlayer;
        this.songService = songService;
        this.playlistServices = playlistServices;
        this.artistService = artistService;
        this.printService = printService;

        this.sequentialState = new SequentialState(this);
        this.shuffleState = new ShuffleState(this);
        this.repeatState = new RepeatState(this);
        this.currentState = this.sequentialState;

        musicPlayer.addSongEndObserver(this::next);
    }

    @Override
    public void setSequentialMode(){
        currentState = this.sequentialState;
        songIdHistory.clear();
        songIdHistory.add(songService.getCurrentSongId());
        currentHistoryIndex = 0;
        printLNBlue(" Repeat All lecture mode activate.");
    }

    @Override
    public void setShuffleMode(){
        currentState = this.shuffleState;
        songIdHistory.clear();
        songIdHistory.add(songService.getCurrentSongId());
        currentHistoryIndex = 0;
        printLNBlue("Shuffle lecture mode activate.");
    }

    @Override
    public void setRepeatMode(){
        currentState = this.repeatState;
        songIdHistory.clear();
        songIdHistory.add(songService.getCurrentSongId());
        currentHistoryIndex = 0;
        printLNBlue(" Repeat One lecture mode activate.");
    }

    @Override
    public int getCurrentPlaylistId() {
        return playlistServices.getCurrentPlaylistId();
    }

    @Override
    public int getCurrentSongId() {
        return currentSong.getSongId();
    }

    @Override
    public void playOrPause(int songId) {
        this.currentPlaylist = playlistServices.getPlaylistById(playlistServices.getCurrentPlaylistId());
        this.currentSong = songService.getSongById(songId);

        if (lastPlayedPlaylistId != currentPlaylist.getPlaylistId()) {
            // historic is cleared when changing playlist
            songIdHistory.clear();
            currentHistoryIndex = -1;
        }
        playlistServices.setCurrentPlaylistId(this.currentPlaylist.getPlaylistId());
        lastPlayedPlaylistId = currentPlaylist.getPlaylistId();

        musicPlayer.playOrPause(this.currentSong.getAudioFileName());
        if (isPlaying()){
            printCurrentSong();
        }
        if (isPaused()){
            printLNWhite("Song paused.");
        }
    }

    @Override
    public void play(int playlistId, int songId) {
        this.currentPlaylist = playlistServices.getPlaylistById(playlistId);
        playlistServices.setCurrentPlaylistId(this.currentPlaylist.getPlaylistId());

        this.currentSong = songService.getSongById(songId);
        musicPlayer.play(this.currentSong.getAudioFileName());
    }

    @Override
    public void playback() {
        if (this.currentSong==null){
            int songId = songService.getCurrentSongId();
            this.currentSong = songService.getSongById(songId);
        }

        printCurrentSong();
        musicPlayer.play(this.currentSong.getAudioFileName());
    }

    @Override
    public void next() {
        if (this.currentSong==null){
            int songId = songService.getCurrentSongId();
            this.currentSong = songService.getSongById(songId);
        }

        this.currentSong = currentState.getNextSong();
        songService.setCurrentSongId(this.currentSong.getSongId());
        printCurrentSong();
        musicPlayer.play(this.currentSong.getAudioFileName());
    }

    @Override
    public void previous() {
        if (this.currentSong==null){
            int songId = songService.getCurrentSongId();
            this.currentSong = songService.getSongById(songId);
        }

        this.currentSong = currentState.getPreviousSong();
        songService.setCurrentSongId(this.currentSong.getSongId());
        printCurrentSong();
        musicPlayer.play(this.currentSong.getAudioFileName());
    }

    public void stop (){
        musicPlayer.stop();
    }

    @Override
    public boolean isPlaying() {
        return musicPlayer.isPlaying();
    }

    @Override
    public boolean isPaused() {
        return musicPlayer.isPaused();
    }

    public String getCurrentState() {
        return currentState.getStateName();
    }

    public void printCurrentSong(){
        int currentSongId = this.getCurrentSongId();
        Song song = songService.getSongById(currentSongId);
        printLNBlue("Current " + printService.printSong(song));
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }
}