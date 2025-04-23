package player_commandPattern;

import player_commandPattern.commands.ICommand;
import player_commandPattern.receivers.SpotifyService;

import java.util.*;

public class SpotifyPlayer {
    private final SpotifyService spotifyService;

    public SpotifyPlayer(SpotifyService spotifyService, Stack<ICommand> commandHistory) {
        this.spotifyService = spotifyService;
    }

    public void selectNext() {
        spotifyService.next();
    }
    public void selectPause() {
        spotifyService.pause();
    }

    public void selectPlay() {
        spotifyService.play();
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
