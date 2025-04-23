package player_commandPattern.commands.player_state_pattern;

import player_commandPattern.receivers.SpotifyService;

public class Repeat implements IState{
    private final SpotifyService spotifyService;

    public Repeat(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    public void replay() {
        spotifyService.play();
    }

    @Override
    public void next() {
        replay();
    }

    @Override
    public void previous() {
        replay();
    }

    @Override
    public void playback() {
        replay();
    }
}
