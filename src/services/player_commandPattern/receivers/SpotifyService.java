package services.player_commandPattern.receivers;

import data.entities.Playlist;
import data.storage.PlaylistRepository;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import services.player_commandPattern.commands.player_state_pattern.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;

public class SpotifyService {

    private Stack<Integer> songHistoricByIndex;
    private int currentPlaylist;
    private int currentSong;
    private static Player player;
    private static Thread playerThread;
    private static boolean isPlaying = false;

    public Stack<Integer> getSongHistoricByIndex() {
        return songHistoricByIndex;
    }

    public int getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(int currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public int getCurrentSong() {
        return currentSong;
    }

    public int getIndexCurrentSong() {
        PlaylistRepository playlistRepository = new PlaylistRepository();
        Playlist playlist = playlistRepository.findPlaylistById(currentPlaylist);
        if (playlist != null) {
            return playlist.getPlaylistSongs().indexOf(currentSong);
        }
        else {
            System.err.println("Pas de Playlist trouvé.");
        }
        return -1; // Retourne -1 si la playlist n'est pas trouvée
    }

    public void setCurrentSong(int currentSong) {
        this.currentSong = currentSong;
    }

    //STATE PATTERN
    private IState currentState;
    private IState sequentialState;
    private IState shuffleState;
    private IState repeatState;

    public Playlist playlist;

    public SpotifyService(int currentSong, int currentPlaylist) {
        this.currentSong = currentSong;
        this.currentPlaylist = currentPlaylist;
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
        setCurrentSong(playlist.getPlaylistSongs().get(songIndex));
        //addition of the currentSong index in the sonHistoricByIndex
        songHistoricByIndex.push(playlist.getPlaylistSongs().indexOf(currentSong));

        try {
            FileInputStream audioFile = new FileInputStream("src/datas/songsfiles/boneyM_Sunny.mp3"); // TODO : sera a changer pour currentSong
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
            System.out.println("Lecture arrêtée.");
        }
    }

    public void playback() {
        currentState.playback();
        System.out.println("Playbackbutton pushed.");
        stop();
        play(getIndexCurrentSong());
    }

    public void next() {
        currentState.next();
        System.out.println("Nextbutton pushed.");
    }

    public void previous(){
        currentState.previous();
        System.out.println("Previousbutton pushed.");
    }

    public void shuffle() {
        if (this.getCurrentState()== this.shuffleState) {
            setCurrentState(sequentialState);
            System.out.println("Shufflebutton pushed again = SequentialPlay mode.");

        }
        else {
            setCurrentState(shuffleState);
            System.out.println("Shufflebutton pushed.");
        }
    }

    public void repeat() {
        if (this.getCurrentState()== this.repeatState) {
            setCurrentState(sequentialState);
            System.out.println("Repeatbutton pushed again = SequentialPlay mode.");
        }
        else {
            setCurrentState(repeatState);
            System.out.println("Repeatbutton pushed.");
        }
    }
}
