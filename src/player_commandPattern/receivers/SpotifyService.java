package player_commandPattern.receivers;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import player_commandPattern.commands.player_state_pattern.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class SpotifyService {

    private Stack<Integer> songHistoricByIndex;
    private int currentPlaylistId;
    private int currentSongId;
    private Player player;
    private Thread playerThread;
    private boolean isPlaying = false;

    public Stack<Integer> getSongHistoricByIndex() {
        return songHistoricByIndex;
    }

    public int getCurrentPlaylistId() {
        return currentPlaylistId;
    }

    public void setCurrentPlaylistId(int currentPlaylistId) {
        this.currentPlaylistId = currentPlaylistId;
    }

    public int getCurrentSongId() {
        return currentSongId;
    }

    public int getIndexCurrentSong() {
        PlaylistRepository playlistRepository = new PlaylistRepository();
        Playlist playlist = playlistRepository.findPlaylistById(currentPlaylistId);
        if (playlist != null) {
            return playlist.getPlaylistSongsId().indexOf(currentSongId);
        }
        else {
            System.err.println("No playlist found.");
        }
        return -1; // Retourne -1 si la playlist n'est pas trouvée
    }

    public void setCurrentSongId(int currentSongId) {
        this.currentSongId = currentSongId;
    }

    //STATE PATTERN
    private IState currentState;
    private IState sequentialState;
    private IState shuffleState;
    private IState repeatState;

    public Playlist playlist;

    public SpotifyService(int currentSongId, int currentPlaylistId) {
        this.currentSongId = currentSongId;
        this.currentPlaylistId = currentPlaylistId;
        this.sequentialState = new Sequential(this);
        this.shuffleState = new Shuffle(this);
        this.repeatState = new Repeat(this);
    }

    public void stateInitiation(SpotifyService spotifyService) {
        spotifyService.setCurrentState(spotifyService.getSequentialState());
    }

    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public IState getSequentialState() {
        return sequentialState;
    }

    public IState getCurrentState() {
        return currentState;
    }

    public IState getShuffleState() {
        return shuffleState;
    }

    public IState getRepeatState() {
        return repeatState;
    }

    //COMMAND PATTERN
    public void play(int songIndex) {
        //Change of the currentSong
        setCurrentSongId(playlist.getPlaylistSongsId().get(songIndex));
        //addition of the currentSong index in the sonHistoricByIndex
        songHistoricByIndex.push(playlist.getPlaylistSongsId().indexOf(currentSongId));

        try {
            FileInputStream audioFile = new FileInputStream(String.valueOf(currentSongId));
            player = new Player(audioFile);

            playerThread = new Thread(() -> {
                try {
                    isPlaying = true;
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                } finally {
                    isPlaying = false;
                }
            });
            playerThread.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //TODO : Pause the player
    }

    public void stop (){
        if (player != null) {
            player.close();
            playerThread.interrupt();
            isPlaying = false;
        }
    }

    public void playback() {
        currentState.playback();
        stop();
        play(getIndexCurrentSong());
    }

    public void next() {
        currentState.next();
    }

    public void previous(){
        currentState.previous();
    }

    public void shuffle() {
        if (this.getCurrentState()== this.shuffleState) {
            setCurrentState(sequentialState);

        }
        else {
            setCurrentState(shuffleState);
        }
    }

    public void repeat() {
        if (this.getCurrentState()== this.repeatState) {
            setCurrentState(sequentialState);
        }
        else {
            setCurrentState(repeatState);
        }
    }
}
