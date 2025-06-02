package clientSide.player.playlist_player;

import clientSide.services.*;
import common.entities.Playlist;
import common.entities.Song;
import clientSide.player.file_player.*;
import common.repository.IAudioRepository;

import java.util.*;

import static clientSide.services.PrintHelper.*;

public class PlaylistPlayer implements IPlaylistPlayer {

    private final IMusicPlayer musicPlayer;
    protected PlaylistServices playlistServices;
    protected ArtistService artistService;
    protected SongService songService;
    protected IAudioRepository audioRepository;

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

    public PlaylistPlayer(IMusicPlayer musicPlayer, IAudioRepository audioRepository, SongService songService,
                          PlaylistServices playlistServices, ArtistService artistService) {
        this.musicPlayer = musicPlayer;
        this.songService = songService;
        this.playlistServices = playlistServices;
        this.artistService = artistService;
        this.audioRepository = audioRepository;

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
        printCurrentSong();
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
        printCurrentSong();
        musicPlayer.play(this.currentSong.getAudioFileName());
    }

    @Override
    public void next() {
        this.currentSong = currentState.getNextSong();
        songService.setCurrentSongId(this.currentSong.getSongId());
        printCurrentSong();
        musicPlayer.play(this.currentSong.getAudioFileName());
    }

    @Override
    public void previous() {
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
        String songTitle = songService.getSongById(currentSongId).getTitle();

        printLNBlue("Current song : " + songTitle + " - " +
                artistService.getArtistNameBySong(currentSongId) + ". ");
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }
}