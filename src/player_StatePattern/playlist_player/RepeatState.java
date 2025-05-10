package player_StatePattern.playlist_player;

import data.entities.Song;

class RepeatState implements IState{
    private final PlaylistPlayer context;

    public RepeatState(PlaylistPlayer spotifyService) {
        this.context = spotifyService;
    }

    @Override
    public Song getNextSong() {
        return context.currentSong;
    }

    @Override
    public Song getPreviousSong (){
        return context.currentSong;
    }
}
