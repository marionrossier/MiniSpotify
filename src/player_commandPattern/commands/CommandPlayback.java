package player_commandPattern.commands;

import player_commandPattern.receivers.SpotifyService;

public class CommandPlayback implements ICommand {
    private SpotifyService spotifyService;

    @Override
    public void execute(String button) {
        spotifyService.playback();
    }
}
