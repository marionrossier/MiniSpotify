package player_commandPattern.commands.player_state_pattern;

import player_commandPattern.recievers.SpotifyService;

public class Repeat implements IState{
    private SpotifyService spotifyService;

    public Repeat(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    public void replay(){
        int currentSongIndex = spotifyService
                .getCurrentPlaylist()
                .getPlaylistSongs()
                .indexOf(spotifyService.getCurrentSong());
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
