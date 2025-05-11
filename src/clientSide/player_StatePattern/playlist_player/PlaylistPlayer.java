package clientSide.player_StatePattern.playlist_player;

import clientSide.services.*;
import serverSide.entities.Playlist;
import serverSide.entities.Song;
import serverSide.repositories.*;
import clientSide.player_StatePattern.file_player.IMusicPlayer;

import java.util.*;

public class PlaylistPlayer implements IPlaylistPlayer {

    private final IMusicPlayer musicPlayer;
    protected PlaylistServices playlistServices;
    protected SongService songService;
    protected IAudioRepository audioRepository;
    private final Icon icon = new Icon();

    protected Stack<Integer> songIdHistory = new Stack<>();

    protected Song currentSong;
    protected Playlist currentPlaylist;

    //STATE PATTERN
    private IState currentState;
    private final IState sequentialState;
    private final IState shuffleState;
    private final IState repeatState;

    public PlaylistPlayer(IMusicPlayer musicPlayer, IAudioRepository audioRepository, SongService songService,
                          PlaylistServices playlistServices) {
        this.musicPlayer = musicPlayer;
        this.songService = songService;
        this.playlistServices = playlistServices;
        this.audioRepository = audioRepository;

        this.sequentialState = new SequentialState(this);
        this.shuffleState = new ShuffleState(this);
        this.repeatState = new RepeatState(this);
        this.currentState = this.sequentialState;

        musicPlayer.setOnSongEndAction(this::next);
    }

    @Override
    public void setSequentialMode(){
        currentState = this.sequentialState;
        System.out.println(icon.sequential() + " Repeat All lecture mode activate.");
    }

    @Override
    public void setShuffleMode(){
        currentState = this.shuffleState;
        System.out.println(icon.shuffle() + "Shuffle lecture mode activate.");
    }

    @Override
    public void setRepeatMode(){
        currentState = this.repeatState;
        System.out.println(icon.repeatOne() + " Repeat One lecture mode activate.");
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
        musicPlayer.play(this.currentSong.getAudioFileName());
    }

    @Override
    public void next() {
        this.songIdHistory.push(currentSong.getSongId());
        this.currentSong = currentState.getNextSong();
        songService.setCurrentSongId(this.currentSong.getSongId());
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
}
