package services.player_commandPattern.commands;

import services.player_commandPattern.receivers.SpotifyService;

public class CommandPlay implements ICommand {
    private SpotifyService spotifyService;
    int songIndex = spotifyService
            .getCurrentPlaylist()
            .getPlaylistSongs()
            .indexOf(spotifyService.getCurrentSong());

    @Override
    public void execute(String button) {
        spotifyService.play(songIndex);
    }
}
