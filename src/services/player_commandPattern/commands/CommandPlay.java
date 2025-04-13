package services.player_commandPattern.commands;

import data.entities.Playlist;
import data.storage.PlaylistRepository;
import services.player_commandPattern.receivers.SpotifyService;

public class CommandPlay implements ICommand {
    private SpotifyService spotifyService;
    int playlistId = spotifyService.getCurrentPlaylistId();
    PlaylistRepository playlistRepository = new PlaylistRepository();
    Playlist playlist = playlistRepository.findPlaylistById(playlistId);
    int songIndex = playlist.getPlaylistSongs().indexOf(spotifyService.getCurrentSongId());

    @Override
    public void execute(String button) {
        spotifyService.play(songIndex);
    }
}
