package services.player_commandPattern.commands.player_state_pattern;

import data.entities.Playlist;
import data.storage.PlaylistRepository;
import services.player_commandPattern.receivers.SpotifyService;

public class Sequential implements IState{
    private final SpotifyService spotifyService;
    private PlaylistRepository playlistRepository = new PlaylistRepository();


    public Sequential(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public void next() {
        Playlist currentPlaylist = playlistRepository.findPlaylistById(spotifyService.getCurrentPlaylist());
        int nextIndex = spotifyService.getIndexCurrentSong() + 1;
        if (nextIndex > currentPlaylist.getPlaylistSongs().size() - 1) {
            nextIndex = 0;
        }
        spotifyService.play(nextIndex);
    }

    @Override
    public void previous() {
        Playlist currentPlaylist = playlistRepository.findPlaylistById(spotifyService.getCurrentPlaylist());
        int previousIndex = spotifyService.getIndexCurrentSong() - 1;
        if (previousIndex < 0) {
            previousIndex = currentPlaylist.getPlaylistSongs().size() - 1;
        }
        spotifyService.play(previousIndex);
    }

    @Override
    public void playback() {
        spotifyService.play(spotifyService.getIndexCurrentSong());
    }
}
