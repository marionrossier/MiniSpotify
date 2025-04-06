package player_commandPattern.commands.player_state_pattern;

import player_commandPattern.recievers.SpotifyService;

public class Sequential implements IState{
    private final SpotifyService spotifyService;

    public Sequential(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public void next() {
        int nextIndex = spotifyService.getIndexCurrentSong()+1;
        if (nextIndex > spotifyService.getCurrentPlaylist().getPlaylistSongs().size()-1){
            nextIndex = 0;
        }
        else {
            spotifyService.play(nextIndex);
        }
        spotifyService.play(nextIndex);
    }

    @Override
    public void previous() {
        int previousIndex = spotifyService.getIndexCurrentSong()-1;
        if (previousIndex<0){
            previousIndex = spotifyService.getCurrentPlaylist().getPlaylistSongs().size()-1;
        }
        else {
            spotifyService.play(previousIndex);
        }
        spotifyService.play(previousIndex);
    }

    @Override
    public void playback() {
        spotifyService.play(spotifyService.getIndexCurrentSong());
    }
}
