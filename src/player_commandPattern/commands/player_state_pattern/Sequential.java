package player_commandPattern.commands.player_state_pattern;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import player_commandPattern.receivers.SpotifyService;
import services.Cookies_SingeltonPattern;

public class Sequential implements IState{
    private final SpotifyService spotifyService;
    private final PlaylistRepository playlistRepository = new PlaylistRepository();


    public Sequential(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public void next() {
        Playlist currentPlaylist = playlistRepository.getPlaylistById(Cookies_SingeltonPattern.getInstance().getCurrentPlaylistId());

        int nextIndex = spotifyService.getIndexCurrentSong() + 1;
        if (nextIndex > currentPlaylist.getPlaylistSongsId().size() - 1) {
            nextIndex = 0;
        }
        int nextSongId = currentPlaylist.getPlaylistSongsId().get(nextIndex);
        Cookies_SingeltonPattern.setCurrentSongId(nextSongId);
        spotifyService.addToSongHistoricByCookies();

        spotifyService.play();
    }

    @Override
    public void previous() {
        spotifyService.getSongHistoricByIndex().pop();

        Playlist currentPlaylist = playlistRepository.getPlaylistById(Cookies_SingeltonPattern.getInstance().getCurrentPlaylistId());

        int previousIndex = spotifyService.getIndexCurrentSong() - 1;
        if (previousIndex < 0) {
            previousIndex = currentPlaylist.getPlaylistSongsId().size() - 1;
        }
        int previousSongId = currentPlaylist.getPlaylistSongsId().get(previousIndex);
        Cookies_SingeltonPattern.setCurrentSongId(previousSongId);

        spotifyService.play();
    }

    @Override
    public void playback() {
        spotifyService.play();
    }
}
