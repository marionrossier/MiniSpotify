package player_commandPattern.commands.player_state_pattern;

import player_commandPattern.receivers.SpotifyService;

public class Shuffle implements IState {
    private SpotifyService spotifyService;

    public Shuffle(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public void next() {
        int nextIndex = spotifyService.getIndexCurrentSong();

        while (nextIndex==spotifyService.getIndexCurrentSong()){
            nextIndex = (int) (Math.random()*spotifyService.getCurrentPlaylist().getPlaylistSongs().size()-1);
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
