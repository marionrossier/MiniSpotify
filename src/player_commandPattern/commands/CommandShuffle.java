package player_commandPattern.commands;

import player_commandPattern.recievers.SpotifyService;

public class CommandShuffle implements ICommand{
    private SpotifyService spotifyService;

    @Override
    public void execute(String button) {
        spotifyService.shuffle();
    }
}
