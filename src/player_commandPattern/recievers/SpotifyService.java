package player_commandPattern.recievers;

import entities.*;
import player_commandPattern.commands.player_state_pattern.*;

import java.util.Stack;

public class SpotifyService {

    private Stack<Integer> songHistoricByIndex;
    private Playlist currentPlaylist;
    private Song currentSong;
    public Stack<Integer> getSongHistoricByIndex() {
        return songHistoricByIndex;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public int getIndexCurrentSong()  {
        return currentPlaylist
                .getPlaylistSongs()
                .indexOf(currentSong);
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    //STATE PATTERN
    private IState currentState;
    private IState sequentialState;
    private IState shuffleState;
    private IState repeatState;

    public Playlist playlist;

    public SpotifyService(Song currentSong, Playlist currentPlaylist) {
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

        //Miss play action of the song.
        /*TODO:Direct implementation*/

        System.out.println("Playbutton pushed.");
    }

    public void pause() {
        /*TODO:Direct implementation*/
        System.out.println("Pausebutton pushed.");
    }

    public void playback() {
        currentState.playback();
        System.out.println("Playbackbutton pushed.");
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
