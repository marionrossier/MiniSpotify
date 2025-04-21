package player_commandPattern.commands.player_state_pattern;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import player_commandPattern.receivers.SpotifyService;

public class Sequential implements IState{
    private final SpotifyService spotifyService;
    private final PlaylistRepository playlistRepository = new PlaylistRepository();


    public Sequential(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public void next() {
        Playlist currentPlaylist = playlistRepository.findPlaylistById(spotifyService.getCurrentPlaylistId());
        int nextIndex = spotifyService.getIndexCurrentSong() + 1;
        if (nextIndex > currentPlaylist.getPlaylistSongsId().size() - 1) {
            nextIndex = 0;
        }
        spotifyService.play(nextIndex);
    }

    @Override
    public void previous() {
        Playlist currentPlaylist = playlistRepository.findPlaylistById(spotifyService.getCurrentPlaylistId());
        int previousIndex = spotifyService.getIndexCurrentSong() - 1;
        if (previousIndex < 0) {
            previousIndex = currentPlaylist.getPlaylistSongsId().size() - 1;
        }
        spotifyService.play(previousIndex);
    }

    @Override
    public void playback() {
        spotifyService.play(spotifyService.getIndexCurrentSong());
    }
}
