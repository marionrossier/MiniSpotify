package player_commandPattern;

import player_commandPattern.commands.ICommand;
import player_commandPattern.receivers.SpotifyService;

import java.util.*;

public class SpotifyPlayer {
    private final SpotifyService spotifyService;
    private int songIndex;

    public SpotifyPlayer(SpotifyService spotifyService, Stack<ICommand> commandHistoric) {
        this.spotifyService = spotifyService;
        this.songIndex = spotifyService.getIndexCurrentSong();
    }
    public void selectNext() {
        spotifyService.next();
    }
    public void selectPause() {
        spotifyService.pause();
    }

    //TODO : retirer le paramètre songIndex ? possible ?
    public void selectPlay(int songIndex) {
        spotifyService.play(songIndex);
    }
    public void selectPlayback() {
        spotifyService.playback();
    }
    public void selectPrevious() {
        spotifyService.previous();
    }
    public void selectRepeat() {
        spotifyService.repeat();
    }

    public void selectShuffle() {
        spotifyService.shuffle();
    }
 }
