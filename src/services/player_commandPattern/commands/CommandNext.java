package services.player_commandPattern.commands;

import services.player_commandPattern.receivers.SpotifyService;

public class CommandNext implements ICommand {
    private SpotifyService spotifyService;

    @Override
    public void execute(String button) {
        spotifyService.next();
    }
}
