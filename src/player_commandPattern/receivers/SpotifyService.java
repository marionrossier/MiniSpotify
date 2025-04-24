package player_commandPattern.receivers;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import player_commandPattern.commands.player_state_pattern.*;
import services.Cookies_SingeltonPattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class SpotifyService {

    private Stack<Integer> songHistoricByIndex;
    private Player player;
    private Thread playerThread;
    private boolean isPlaying = false;
    private SongRepository songRepository = new SongRepository();

    public Stack<Integer> getSongHistoricByIndex() {
        return songHistoricByIndex;
    }

    public void addToSongHistoricByCookies() {
        songHistoricByIndex.push(playlist.getPlaylistSongsListWithId().indexOf(Cookies_SingeltonPattern.getInstance().getCurrentSongId()));
    }

    public int getIndexCurrentSong() {
        PlaylistRepository playlistRepository = new PlaylistRepository();
        Playlist playlist = playlistRepository.getPlaylistById(Cookies_SingeltonPattern.resetCookies().getCurrentPlaylistId());
        if (playlist != null) {
            return playlist.getPlaylistSongsListWithId().indexOf(Cookies_SingeltonPattern.getInstance().getCurrentSongId());
        }
        else {
            System.err.println("No playlist found.");
            return -1;
        }
    }


    //STATE PATTERN
    private IState currentState;
    private IState sequentialState;
    private IState shuffleState;
    private IState repeatState;

    public Playlist playlist;

    public SpotifyService() {
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
    //TODO : attention, car je suis pas sur qu'il prenne la chanson...

    public void play() {

        try {
            String audioFilePath = songRepository
                    .getSongById(Cookies_SingeltonPattern.getInstance().getCurrentSongId())
                    .getAudioFilePath();
            FileInputStream audioFile = new FileInputStream(audioFilePath);
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
            System.out.println("Lecture démarrée.");
            new Scanner(System.in).nextLine();

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
        play();
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
