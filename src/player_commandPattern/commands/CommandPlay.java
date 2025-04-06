package player_commandPattern.commands;

import player_commandPattern.recievers.SpotifyService;

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
