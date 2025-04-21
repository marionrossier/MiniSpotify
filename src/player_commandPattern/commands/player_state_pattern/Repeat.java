package player_commandPattern.commands.player_state_pattern;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import player_commandPattern.receivers.SpotifyService;

public class Repeat implements IState{
    private final SpotifyService spotifyService;
    private PlaylistRepository playlistRepository = new PlaylistRepository();


    public Repeat(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    public void replay() {
        Playlist currentPlaylist = playlistRepository.findPlaylistById(spotifyService.getCurrentPlaylistId());
        int currentSongIndex = currentPlaylist.getPlaylistSongsId()
                .indexOf(spotifyService.getCurrentSongId());
        spotifyService.play(currentSongIndex);
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
