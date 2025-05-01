package player_StatePattern.playlist_player;

import data.entities.Song;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import player_StatePattern.file_player.IMusicPlayer;
import services.Cookies_SingletonPattern;

import java.util.*;

public class PlaylistPlayer implements IPlaylistPlayer {

    private final IMusicPlayer musicPlayer;
    protected SongRepository songRepository;
    protected final PlaylistRepository playlistRepository;

    protected Stack<Integer> songIdHistory = new Stack<>();
    protected Song currentSong;
    protected int currentPlaylistId;

    //STATE PATTERN
    private IState currentState;
    private final IState sequentialState;
    private final IState shuffleState;
    private final IState repeatState;

    public PlaylistPlayer(IMusicPlayer musicPlayer, SongRepository songRepository, PlaylistRepository playlistRepository) {
        this.musicPlayer = musicPlayer;
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;

        this.sequentialState = new SequentialState(this);
        this.shuffleState = new ShuffleState(this);
        this.repeatState = new RepeatState(this);
        this.currentState = this.sequentialState;

        musicPlayer.setOnSongEndAction(this::next);
    }

    @Override
    public void setSequentialMode(){
        currentState = this.sequentialState;
    }

    @Override
    public void setShuffleMode(){
        currentState = this.shuffleState;
    }

    @Override
    public void setRepeatMode(){
        currentState = this.repeatState;
    }
    @Override
    public int getRunningPlaylistId() {
        currentPlaylistId =Cookies_SingletonPattern.getInstance().getCurrentPlaylistId();
        return currentPlaylistId;
    }

    @Override
    public int getRunningSongId() {
        return currentSong.getSongId();
    }

    @Override
    public void playOrPause(int songId) {
        this.currentSong = songRepository.getSongById(songId);
        Cookies_SingletonPattern.setCurrentSongId(this.currentSong.getSongId());
        musicPlayer.playOrPause(currentSong.getAudioFilePath());
    }

    @Override
    public void play(int playlistId, int songId) {
        this.currentPlaylistId = playlistId;
        Cookies_SingletonPattern.setCurrentPlaylistId(this.currentPlaylistId);

        this.currentSong = songRepository.getSongById(songId);
        musicPlayer.play(currentSong.getAudioFilePath());
    }

    @Override
    public void pause() {
        musicPlayer.pause();
    }

    @Override
    public void resume(int currentSongId) {
        this.currentSong = songRepository.getSongById(currentSongId);
        musicPlayer.resume(currentSong.getAudioFilePath());
    }

    @Override
    public void playback() {
        musicPlayer.play(this.currentSong.getAudioFilePath());
    }

    @Override
    public void next() {
        this.songIdHistory.push(currentSong.getSongId());
        this.currentSong = currentState.getNextSong();
        Cookies_SingletonPattern.setCurrentSongId(this.currentSong.getSongId());
        this.musicPlayer.play(this.currentSong.getAudioFilePath());
    }

    @Override
    public void previous(){
        if (songIdHistory.isEmpty()) return;
        int previousSongId = songIdHistory.pop();
        this.currentSong = songRepository.getSongById(previousSongId);
        Cookies_SingletonPattern.setCurrentSongId(this.currentSong.getSongId());
        this.musicPlayer.play(this.currentSong.getAudioFilePath());
    }

    @Override
    public IMusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}
