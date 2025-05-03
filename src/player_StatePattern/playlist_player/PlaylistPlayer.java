package player_StatePattern.playlist_player;

import data.entities.Playlist;
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
    protected Playlist currentPlaylist;

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
        return currentPlaylist.getPlaylistId();
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
        this.currentPlaylist = playlistRepository.getPlaylistById(playlistId);
        Cookies_SingletonPattern.setCurrentPlaylistId(this.currentPlaylist.getPlaylistId());

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
        if (currentState == repeatState){
            this.currentSong = currentState.getNextSong();
        }
        else {
            if (songIdHistory.isEmpty()) return;
            int previousSongId = songIdHistory.pop();
            this.currentSong = songRepository.getSongById(previousSongId);
        }
        Cookies_SingletonPattern.setCurrentSongId(this.currentSong.getSongId());
        this.musicPlayer.play(this.currentSong.getAudioFilePath());
    }

    public void stop (){
        musicPlayer.stop();
    }

    @Override
    public IMusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}
