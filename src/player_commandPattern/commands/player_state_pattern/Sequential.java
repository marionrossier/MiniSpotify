package player_commandPattern.commands.player_state_pattern;

import entities.*;
import player_commandPattern.recievers.SpotifyService;

public class Sequential implements IState{
    private SpotifyService spotifyService;
    private Playlist currentPlaylist;
    private Song currentSong;
    int indexCurrentSong = currentPlaylist
                                .getPlaylistSongs()
                                .indexOf(currentSong);

    public Sequential(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
        this.currentPlaylist = spotifyService.getCurrentPlaylist();
        this.currentSong = spotifyService.getCurrentSong();
    }

    @Override
    public void next() {
        int nextIndex = indexCurrentSong+1;
        if (nextIndex > currentPlaylist.getPlaylistSongs().size()-1){
            nextIndex = 0;
        }
        else {
            spotifyService.play(nextIndex);
        }
        spotifyService.play(nextIndex);
    }

    @Override
    public void previous() {
        int previousIndex = indexCurrentSong-1;
        if (previousIndex<0){
            previousIndex = currentPlaylist.getPlaylistSongs().size()-1;
        }
        else {
            spotifyService.play(previousIndex);
        }
        spotifyService.play(previousIndex);
    }

    @Override
    public void playback() {
        spotifyService.play(indexCurrentSong);
    }
}
