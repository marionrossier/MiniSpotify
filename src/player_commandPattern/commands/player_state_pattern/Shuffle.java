package player_commandPattern.commands.player_state_pattern;

import entities.Playlist;
import entities.Song;
import player_commandPattern.recievers.SpotifyService;

public class Shuffle implements IState {
    private SpotifyService spotifyService;
    private Playlist currentPlaylist;
    private Song currentSong;
    int indexCurrentSong = currentPlaylist
            .getPlaylistSongs()
            .indexOf(currentSong);

    public Shuffle(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
        this.currentPlaylist = spotifyService.getCurrentPlaylist();
        this.currentSong = spotifyService.getCurrentSong();
    }

    @Override
    public void next() {
        int nextIndex = indexCurrentSong;

        while (nextIndex==indexCurrentSong){
            nextIndex = (int) (Math.random()*currentPlaylist.getPlaylistSongs().size()-1);
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
        spotifyService.play(indexCurrentSong);
    }
}
