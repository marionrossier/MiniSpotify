package player_commandPattern.commands.player_state_pattern;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import player_commandPattern.receivers.SpotifyService;
import services.Cookies_SingeltonPattern;

public class Shuffle implements IState {
    private final SpotifyService spotifyService;
    private final PlaylistRepository playlistRepository = new PlaylistRepository();

    public Shuffle(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public void next() {
        Playlist currentPlaylist = playlistRepository.getPlaylistById(Cookies_SingeltonPattern.getInstance().getCurrentPlaylistId());

        int nextIndex = spotifyService.getIndexCurrentSong();

        while (nextIndex == spotifyService.getIndexCurrentSong()) {
            nextIndex = (int) (Math.random() * currentPlaylist.getPlaylistSongsListWithId().size());
        }
        int nextSongId = currentPlaylist.getPlaylistSongsListWithId().get(nextIndex);
        Cookies_SingeltonPattern.setCurrentSongId(nextSongId);
        spotifyService.addToSongHistoricByCookies();

        spotifyService.play();
    }

    @Override
    public void previous() {
        spotifyService.getSongHistoricByIndex().pop();
        Playlist currentPlaylist = playlistRepository.getPlaylistById(Cookies_SingeltonPattern.getInstance().getCurrentPlaylistId());
        int previousSongIndex = spotifyService.getSongHistoricByIndex().peek();

        int previousSongId = currentPlaylist.getPlaylistSongsListWithId().get(previousSongIndex);
        Cookies_SingeltonPattern.setCurrentSongId(previousSongId);

        spotifyService.play();
    }

    @Override
    public void playback() {
        spotifyService.play();
    }
}
