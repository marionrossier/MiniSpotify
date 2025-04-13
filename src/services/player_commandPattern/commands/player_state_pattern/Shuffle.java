package services.player_commandPattern.commands.player_state_pattern;

import data.entities.Playlist;
import data.storage.PlaylistRepository;
import services.player_commandPattern.receivers.SpotifyService;

public class Shuffle implements IState {
    private final SpotifyService spotifyService;
    private final PlaylistRepository playlistRepository = new PlaylistRepository();

    public Shuffle(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public void next() {
        int nextIndex = spotifyService.getIndexCurrentSong();
        Playlist currentPlaylist = playlistRepository.findPlaylistById(spotifyService.getCurrentPlaylistId());

        while (nextIndex == spotifyService.getIndexCurrentSong()) {
            nextIndex = (int) (Math.random() * currentPlaylist.getPlaylistSongs().size());
        }
        spotifyService.play(nextIndex);
    }

    @Override
    public void previous() {
        spotifyService.getSongHistoricByIndex().pop();
        int previousSongIndex = spotifyService.getSongHistoricByIndex().peek();

        spotifyService.play(previousSongIndex);
        //Took of one more because it is automatically added again in the play action !
        spotifyService.getSongHistoricByIndex().pop();
    }

    @Override
    public void playback() {
        spotifyService.play(spotifyService.getIndexCurrentSong());
    }
}
