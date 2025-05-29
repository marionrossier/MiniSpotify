package clientSide.player.playlist_player;

import common.*;
import clientSide.services.*;
import common.entities.Playlist;
import common.entities.Song;
import clientSide.player.file_player.*;
import serverSide.repoLocal.SongLocalRepository;

import java.util.*;

import static clientSide.services.PrintHelper.*;

public class PlaylistPlayer implements IPlaylistPlayer {

    private final IMusicPlayer musicPlayer;
    protected PlaylistServices playlistServices;
    protected ArtistService artistService;
    protected SongService songService;
    protected IAudioRepository audioRepository;
    private final IconService icon = new IconService();

    protected Stack<Integer> songIdHistory = new Stack<>();

    protected Song currentSong;
    protected Playlist currentPlaylist;

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
        printLNBlue(icon.sequential() + " Repeat All lecture mode activate.");
    }

    @Override
    public void setShuffleMode(){
        currentState = this.shuffleState;
        printLNBlue(icon.shuffle() + "Shuffle lecture mode activate.");
    }

    @Override
    public void setRepeatMode(){
        currentState = this.repeatState;
        printLNBlue(icon.repeatOne() + " Repeat One lecture mode activate.");
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
        this.currentSong = songService.getSongById(songId);
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
    public void pause() {
        musicPlayer.pause();
    }

    @Override
    public void resume(int currentSongId) {
        this.currentSong = songService.getSongById(currentSongId);
        musicPlayer.resume(this.currentSong.getAudioFileName());
    }

    @Override
    public void playback() {
        printCurrentSong();
        musicPlayer.play(this.currentSong.getAudioFileName());
    }

    @Override
    public void next() {
        this.songIdHistory.push(currentSong.getSongId());
        this.currentSong = currentState.getNextSong();
        songService.setCurrentSongId(this.currentSong.getSongId());
        printCurrentSong();
        this.musicPlayer.play(this.currentSong.getAudioFileName());
    }

    @Override
    public void previous(){
        if (currentState == repeatState){
            this.currentSong = currentState.getNextSong();
        }
        else {
            if (songIdHistory.isEmpty()) return;
            int previousSongId = songIdHistory.pop();
            this.currentSong = songService.getSongById(previousSongId);
        }
        songService.setCurrentSongId(this.currentSong.getSongId());
        printCurrentSong();
        this.musicPlayer.play(this.currentSong.getAudioFileName());
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
        String prefix = this.isPaused() ? "Song paused : " : "Song played : ";
        String songTitle = songService.getSongById(currentSongId).getTitle();

        printLNBlue(prefix + songTitle + " - " +
                artistService.getArtistNameBySong(currentSongId) + ". ");
    }
}