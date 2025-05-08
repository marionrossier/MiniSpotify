package player_StatePattern.playlist_player;

import data.entities.Playlist;
import data.entities.Song;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import player_StatePattern.file_player.IMusicPlayer;
import services.PlaylistServices;
import services.SongService;

import java.util.*;

public class PlaylistPlayer implements IPlaylistPlayer {

    private final IMusicPlayer musicPlayer;
    protected SongRepository songRepository;
    protected final PlaylistRepository playlistRepository;
    protected PlaylistServices playlistServices;
    protected SongService songService;

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
        this.songService = new SongService(songRepository);
        this.playlistServices = new PlaylistServices(playlistRepository);

        this.sequentialState = new SequentialState(this);
        this.shuffleState = new ShuffleState(this);
        this.repeatState = new RepeatState(this);
        this.currentState = this.sequentialState;

        musicPlayer.setOnSongEndAction(this::next);
    }

    @Override
    public void setSequentialMode(){
        currentState = this.sequentialState;
        System.out.println("Sequential lecture mode activate.");
    }

    @Override
    public void setShuffleMode(){
        currentState = this.shuffleState;
        System.out.println("Shuffle lecture mode activate.");
    }

    @Override
    public void setRepeatMode(){
        currentState = this.repeatState;
        System.out.println("RepeateOne lecture mode activate.");
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
        this.currentSong = songRepository.getSongById(songId);
        musicPlayer.playOrPause(currentSong.getAudioFilePath());
    }

    @Override
    public void play(int playlistId, int songId) {
        this.currentPlaylist = playlistRepository.getPlaylistById(playlistId);
        playlistServices.setCurrentPlaylistId(this.currentPlaylist.getPlaylistId());

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
        songService.setCurrentSongId(this.currentSong.getSongId());
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
        songService.setCurrentSongId(this.currentSong.getSongId());
        this.musicPlayer.play(this.currentSong.getAudioFilePath());
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
}
