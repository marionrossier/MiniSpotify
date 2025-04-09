package player_commandPattern;

import entities.Song;
import player_commandPattern.commands.*;
import player_commandPattern.recievers.SpotifyService;

import java.util.*;

public class SpotifyPlayer {
    private final SpotifyService spotifyService;
    private int currentSongIndex;

    public void selectNext() {
        spotifyService.next();
    }
    public void selectPause() {
        spotifyService.pause();
    }
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

    public SpotifyPlayer(SpotifyService spotifyService, Stack<ICommand> commandHistoric) {
        this.spotifyService = spotifyService;
        this.currentSongIndex = spotifyService.getIndexCurrentSong();
    }
 }
